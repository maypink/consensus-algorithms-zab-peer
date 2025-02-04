package org.example.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.catalina.connector.Response;
import org.example.entities.CustomStreamObserver;
import org.example.entities.Node;
import org.example.utilities.LogFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import zab.*;
import zab_peer.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@GrpcService
public class ZabPeerService extends ZabPeerServiceGrpc.ZabPeerServiceImplBase{

    @Autowired
    public Node node;

    private List<String> peersPortsList;
    private ZabPeerServiceGrpc.ZabPeerServiceStub stub;
    private Map<String, ZabPeerServiceGrpc.ZabPeerServiceFutureStub> peerStubs = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<ElectionRequest> voteQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<BankTransaction> transactionQueue = new ConcurrentLinkedQueue<>();
    private ExecutorService voteProcessingExecutor = Executors.newSingleThreadExecutor();
    private ExecutorService transactionProcessingExecutor = Executors.newSingleThreadExecutor();
    private AtomicBoolean electionInProgress = new AtomicBoolean(false);

    private int currentEpoch = 0;
    private int counter = 0;
    private Vote currentVote;
    private ConcurrentHashMap<Integer, Integer> voteCounts = new ConcurrentHashMap<>();
    private long electionStartTime;
    private static final long ELECTION_TIMEOUT_MILLIS = 5000;

    @Autowired
    private LogFormatter logger;

    private long lastElectionFinishTime;

    // Initialize the stubs for peer communication
    public ZabPeerService() {
        this.peersPortsList = new ArrayList<>();
    }

    public void logNodeData(){
        logger.format("Node's id is " + node.getId());
        logger.format("Node's port is " + node.getPort());
    }

    public void initializePeers() {
        System.out.println("Initializing peers for peer " + this.node.getId());


        this.peersPortsList = node.getPeerPorts();

        if (this.voteCounts == null){
            this.voteCounts = new ConcurrentHashMap<>();
        }

        // Create stubs for each peer
        for (String peerPort : peersPortsList) {
            System.out.println("Current peer is " + peerPort);

            // local
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", Integer.parseInt(peerPort))
                    .usePlaintext()
                    .build();

            // docker
//            ManagedChannel channel = ManagedChannelBuilder.forAddress("host.docker.internal", Integer.parseInt(peerPort))
//                    .usePlaintext()
//                    .build();
            ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = ZabPeerServiceGrpc.newFutureStub(channel);
            peerStubs.put(peerPort, stub);
        }
        startVoteProcessingWorker();
    }

    private void startVoteProcessingWorker() {
        voteProcessingExecutor.submit(() -> {
            while (true) {
                // Continuously poll the queue for incoming votes
                ElectionRequest request = voteQueue.poll();
                if (request != null) {
                    processVote(request);
                }
                Thread.sleep(5); // Avoid busy waiting
            }
        });
    }

    private void processVote(ElectionRequest request) {
        synchronized (this) {

            if (request.getState() == State.Leading){
                if (request.getId() == node.getId()){
                    node.setState(State.Leading);
                } else {
                    node.setState(State.Following);
                }
                // If there is still an ongoing election
            } else if (node.getState() == State.Election) {
                // Process the vote
                Vote incomingVote = request.getVote();
                logger.format("Processing vote from Node " + incomingVote.getId());

                // Check if the incoming vote is preferred
                if (isPreferredVote(incomingVote)) {
                    logger.format("Received vote is preferred above the current for node " + node.getId());
                    logger.format("Updating current vote for Node " + node.getId());

                    this.currentVote = incomingVote;
                    // Count new vote
                    this.voteCounts.compute(incomingVote.getId(), (key, count) -> (count == null) ? 1 : count + 1);

                    logger.format("Current votes: " + voteCounts);

                    // Broadcast updated election notification
                    ElectionRequest updatedRequest = ElectionRequest.newBuilder()
                            .setId(node.getId())
                            .setVote(this.currentVote)
                            .setState(State.Election)
                            .build();
                    broadcastToPeers(updatedRequest, new CustomStreamObserver());
                }

                // Check for quorum
                if (isQuorumReached()) {
                    this.counter = 0;
                    this.currentEpoch += 1;
                    concludeElection();
                }

                 // Check if timeout has expired
                if (System.currentTimeMillis() - electionStartTime > ELECTION_TIMEOUT_MILLIS) {
                    logger.format("Election timed out. Retrying...");
                    logger.format(String.valueOf(this.voteCounts));
                    node.setState(State.Election);
                    this.currentEpoch += 1;
                    electionStartTime = System.currentTimeMillis();

                    // Update epoch + 1
                    ZxId zxId = ZxId.newBuilder()
                            .setEpoch(node.getHistory().getLastCommitedZxId().getEpoch() + 1)
                            .setCounter(node.getHistory().getLastCommitedZxId().getCounter())
                            .build();
                    Vote newRoundVote = Vote.newBuilder()
                            .setId(node.getId())
                            .setLastZxId(zxId)
                            .build();
                    ElectionRequest newRoundRequest = ElectionRequest.newBuilder()
                            .setId(node.getId())
                            .setVote(newRoundVote)
                            .setState(State.Election)
                            .build();
                    sendElectionNotification(newRoundRequest, new CustomStreamObserver());
                }
            }
        }
    }

    private int getNewLeaderId() {
        int totalVotes = peersPortsList.size() + 1; // Including this node
        int quorum = totalVotes / 2 + 1;
        for (Map.Entry<Integer, Integer> voteCount: this.voteCounts.entrySet()){
            if (voteCount.getValue() >= quorum){
                return voteCount.getKey();
            }
        }
        return -1;
    }

    private void concludeElection() {
        int newLeaderId = this.getNewLeaderId();
        logger.format("Quorum reached! Node " + newLeaderId + " becomes the leader.");
        electionInProgress.set(false);
        lastElectionFinishTime = System.currentTimeMillis();
        if (node.getId() == newLeaderId){
            node.setState(State.Leading);
        } else {
            node.setState(State.Following);
        }

        // Notify all followers about the new leader
        for (String peerPort : peersPortsList) {
            try {
                ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
//                stub.sendNewLeaderNotification(NewLeaderRequest.newBuilder().setId(node.getId()).build());
                stub.sendElectionNotification(ElectionRequest.newBuilder()
                        .setId(node.getId())
                        .setVote(Vote.newBuilder().setId(node.getId()).setLastZxId(node.getHistory().getLastCommitedZxId()))
                        .setState(State.Leading).build());
            } catch (Exception e) {
                System.err.println("Failed to notify peer " + peerPort + ": " + e.getMessage());
            }
        }
    }

    private void broadcastToPeers(ElectionRequest request, StreamObserver<Empty> done) {
        for (String peerPort : peersPortsList) {
            try {
                ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
                logger.format("Sending vote from node " + node.getId() + " to peer on port " + peerPort + " for node " + request.getVote().getId());
                stub.sendElectionNotification(request);
            } catch (Exception e) {
                System.err.println("Failed to send election notification to peer " + peerPort + ": " + e.getMessage());
            }
        }
    }

    private void sendFirstElectionNotification(long currentTime, ElectionRequest request, StreamObserver<Empty> done){
        // Start a new election if none is ongoing
        if (node.getState() != State.Election || currentTime - this.electionStartTime > ELECTION_TIMEOUT_MILLIS) {
            this.electionInProgress.set(true);
            this.electionStartTime = currentTime;
            this.node.setState(State.Election);

            // Reset vote counts and vote for itself
            logger.format("Resetting votes count...");
            this.voteCounts.clear();
            this.voteCounts.put(node.getId(), 1);
            logger.format(String.valueOf(this.voteCounts));

            Vote vote = Vote.newBuilder()
                    .setId(node.getId())
                    .setLastZxId(node.getHistory().getLastCommitedZxId())
                    .build();
            this.currentVote = vote;
            // Broadcast initial election notification
            ElectionRequest initialRequest = ElectionRequest.newBuilder()
                    .setId(node.getId())
                    .setVote(vote)
                    .setState(State.Election)
                    .setRound(1)
                    .build();
            broadcastToPeers(initialRequest, done);
        }
    }

    @Override
    public void sendElectionNotification(ElectionRequest request, StreamObserver<Empty> done) {
        long currentTime = System.currentTimeMillis();

        // Enqueue the incoming vote for processing
        voteQueue.offer(request);

        // If no ongoing election, start one
        if (!electionInProgress.get() && node.getState() != State.Leading) {
            synchronized (this) {
                if (!electionInProgress.get()) {
                    sendFirstElectionNotification(currentTime, request, done);
                    electionInProgress.set(true);
                }
            }
        }

        done.onNext(Empty.getDefaultInstance());
        done.onCompleted();
    }

    private boolean isPreferredVote(Vote incomingVote) {
        Vote currentVote = Vote.newBuilder()
                .setId(node.getId())
                .setLastZxId(node.getHistory().getLastCommitedZxId())
                .build();

        // Compare based on epoch > counter > node ID
        return incomingVote.getLastZxId().getEpoch() > currentVote.getLastZxId().getEpoch()
                || (incomingVote.getLastZxId().getEpoch() == currentVote.getLastZxId().getEpoch()
                && incomingVote.getLastZxId().getCounter() > currentVote.getLastZxId().getCounter())
                || (incomingVote.getLastZxId().getEpoch() == currentVote.getLastZxId().getEpoch()
                && incomingVote.getLastZxId().getCounter() == currentVote.getLastZxId().getCounter()
                && incomingVote.getId() > currentVote.getId());
    }

    private boolean isQuorumReached() {
        int totalVotes = peersPortsList.size() + 1; // Including this node
        int quorum = totalVotes / 2 + 1;

        for (Integer count : voteCounts.values()) {
            if (count >= quorum) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void getState(Empty request, StreamObserver<GetStateResponse> done) {

        if (node.getState() == State.Election) {
            logger.format("Node " + node.getId() + " is in Election state. Waiting for election to complete.");

            // Start election process
            Vote vote = Vote.newBuilder()
                    .setId(node.getId())
                    .setLastZxId(node.getHistory().getLastCommitedZxId())
                    .build();
            ElectionRequest electionRequest = ElectionRequest.newBuilder()
                    .setState(node.getState())
                    .setId(node.getId())
                    .setVote(vote)
                    .build();
            this.sendElectionNotification(electionRequest, new CustomStreamObserver());

            // Wait for election to complete or timeout
            try {
                boolean electionCompleted = waitForElectionCompletion();
                if (!electionCompleted) {
                    logger.format("Election timeout occurred. Proceeding with current state.");
                    logger.format(String.valueOf(this.voteCounts));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Election process interrupted: " + e.getMessage());
            }
        }

        // Provide the current state of this node
        GetStateResponse response = GetStateResponse.newBuilder()
                .setState(node.getState())
                .build();
        logger.format("State of the " + node.getId() + " node is " + node.getState().name());

        done.onNext(response);
        done.onCompleted();
    }

    /**
     * Waits for election to complete or timeout.
     * @return true if election completed successfully, false if timeout occurred
     */
    private boolean waitForElectionCompletion() throws InterruptedException {
        long electionStartTime = System.currentTimeMillis();
        long timeRemaining;

        synchronized (node) {
            while (node.getState() == State.Election && (timeRemaining = ELECTION_TIMEOUT_MILLIS - (System.currentTimeMillis() - electionStartTime)) > 0) {
                node.wait(timeRemaining);
            }
        }
        return node.getState() != State.Election;
    }

    @Override
    public void sendFollowerInfo(FollowerInfoRequest request, StreamObserver<FollowerInfoResponse> done) {

        if (node.getState() == State.Leading) {
            node.getHistory().toBuilder()
                    .setLastCommitedZxId(ZxId.newBuilder()
                            .setEpoch(node.getHistory().getLastCommitedZxId().getEpoch() + 1)
                            .setCounter(0)
                            .build());

            // Update LastCommittedZxId epoch
            node.getHistory().toBuilder()
                    .setLastCommitedZxId(ZxId.newBuilder()
                            .setEpoch(node.getHistory().getLastCommitedZxId().getEpoch() + 1)
                            .setCounter(node.getHistory().getLastCommitedZxId().getCounter()))
                    .build();

            // Send NEWLEADER(L.lastZxid)
            FollowerInfoResponse followerInfoResponseAck = FollowerInfoResponse.newBuilder()
                    .setLastZxId(node.getHistory().getLastCommitedZxId())
                    .build();
            done.onNext(followerInfoResponseAck);

            //TODO: overload <>= for zxid
            if (request.getLastZxId().getCounter() <= node.getHistory().getLastCommitedZxId().getCounter()) {
                if (request.getLastZxId().getCounter() < node.getHistory().getOldThreshold().getCounter()) {
                    Snap snap = Snap.newBuilder().setHistory(node.getHistory()).build();
                    FollowerInfoResponse response = FollowerInfoResponse.newBuilder().setSnap(snap).build();
                    done.onNext(response);
                } else {
                    // TODO: filter transactions
                    Diff diff = Diff.newBuilder().setTransactions(node.getHistory().getCommitted()).build();
                    FollowerInfoResponse response = FollowerInfoResponse.newBuilder().setDiff(diff).build();
                    done.onNext(response);
                }
            } else {
                Trunc trunc = Trunc.newBuilder().setLastCommitedZxId(node.getHistory().getLastCommitedZxId()).build();
                FollowerInfoResponse response = FollowerInfoResponse.newBuilder().setTrunc(trunc).build();
                done.onNext(response);
            }

            FollowerInfoResponse response = FollowerInfoResponse.newBuilder()
                    .setLastZxId(node.getHistory().getLastCommitedZxId())
                    .build();
            done.onNext(response);
        }
    }

    public void sendFollowerInfoRequest(FollowerInfoResponse response, StreamObserver<FollowerInfoRequest> done) {
        if (node.getState() == State.Following){

            for (String peerPort : peersPortsList) {
                try {
                    ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
                    FollowerInfoRequest request = FollowerInfoRequest.newBuilder()
                            .setLastZxId(node.getHistory().getLastCommitedZxId())
                            .build();

                    FollowerInfoResponse followerInfoResponse = stub.sendFollowerInfo(request).get();
                    logger.format("Sent follower's zxId: " + request.getLastZxId() + " to port " + peerPort);
                    if (followerInfoResponse.getLastZxId().getCounter() < node.getHistory().getLastCommitedZxId().getCounter()) {
                        node.setState(State.Election);
                        this.sendElectionNotification(ElectionRequest.newBuilder().setId(node.getId()).build(), new CustomStreamObserver());
                    }

                    // TODO: process TRUNC, DIFF, SNAP

                    // Sending ACKNEWLEADER
                    stub.sendAckNewLeader(Empty.getDefaultInstance());

                } catch (Exception e) {
                    System.err.println("Failed to send election notification to peer " + peerPort + ": " + e.getMessage());
                }
            }
        } else if (node.getState() == State.Leading) {
            for (String peerPort : peersPortsList) {
                try {
                    // Send notifications to all followers about the new leader
                    ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
                    NewLeaderRequest newLeaderRequest = NewLeaderRequest.newBuilder().setId(node.getId()).build();
                    stub.sendNewLeaderNotification(newLeaderRequest);
                } catch (Exception e) {
                    System.err.println("Failed to send new leader notification to peer " + peerPort + ": " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void sendNewLeaderNotification(NewLeaderRequest request, StreamObserver<Empty> done){
        // Upon receiving notification set new leader to the node
        if (node.getState() != State.Leading){
            node.setState(State.Following);
            node.setLeaderId(request.getId());
            done.onNext(Empty.getDefaultInstance());
        }
    }

    @Override
    public void sendAckNewLeader(Empty request, StreamObserver<Empty> done) {
        if (node.getState() == State.Following){
            logger.format("Sending ACK for new leader...");
            for (String peerPort : peersPortsList) {
                try {
                    ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
                    stub.sendAckNewLeader(Empty.getDefaultInstance());
                } catch (Exception e) {
                    System.err.println("Failed to send ACK to peer " + peerPort + ": " + e.getMessage());
                }
            }
            done.onNext(Empty.getDefaultInstance());
        } else if (node.getState() == State.Leading) {
            done.onNext(request);
        }
    }

    private void startTransactionProcessingWorker() {
        transactionProcessingExecutor.submit(() -> {
            while (true) {
                // Continuously poll the queue for incoming votes
                BankTransaction transaction = transactionQueue.poll();
                if (transaction != null) {
                    processTransaction(transaction);
                }
                Thread.sleep(5); // Avoid busy waiting
            }
        });
    }

    private void processTransaction(BankTransaction transaction) {
        synchronized (this) {

        }
    }

    @Override
    public void proposeTransaction(ProposeTransactionRequest request, StreamObserver<Empty> done) {
        // Start FLE if it wasn't started yet
        if (node.getState() != State.Leading && node.getState() != State.Following) {
            this.sendElectionNotification(ElectionRequest.newBuilder().setId(node.getId()).build(), new CustomStreamObserver());
        }

        ZxId currentZxid;
        // Update the request with leader's current zxid
        // (NOTE: the builder call here needs to build a new request)
        // logger.format("Broadcasting proposed transaction: " + request.getTransaction());

        if (node.getState() == State.Leading) {
            // Update local state: increment counter and generate new zxid
            this.counter += 1;
            currentZxid = ZxId.newBuilder().setEpoch(this.currentEpoch).setCounter(this.counter).build();
            BankTransactionMap bankTransactionMap = node.getHistory().getProposed();
            History history = node.getHistory();
            node.setHistory(history.toBuilder().setProposed(bankTransactionMap.toBuilder()
                    .addEntries(BankTransactionMapEntry
                            .newBuilder()
                            .setKey(currentZxid)
                            .setValue(request.getTransaction())
                            .build())
            ).build());

            // Build the updated propose request with the new ZxId
            ProposeTransactionRequest updatedRequest = request.toBuilder()
                    .setZxId(currentZxid)
                    .build();

            // Prepare quorum details: total nodes and required quorum
            int totalNodes = peersPortsList.size() + 1; // including this leader
            int quorum = totalNodes / 2 + 1;
            // Leader already acknowledges its own proposal
            AtomicInteger ackCount = new AtomicInteger(1);

            // Create a CountDownLatch for all peers
            CountDownLatch latch = new CountDownLatch(peersPortsList.size());
            // Use an executor to send proposals concurrently
            ExecutorService executor = Executors.newFixedThreadPool(peersPortsList.size());

            // Send propose transaction to each peer asynchronously
            for (String peerPort : peersPortsList) {
                executor.submit(() -> {
                    try {

                        // Use blocking stub from a new channel (or consider reusing channels)
                        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", Integer.parseInt(peerPort))
                                .usePlaintext()
                                .build();

                        // docker
//                        ManagedChannel channel = ManagedChannelBuilder.forAddress("host.docker.internal", Integer.parseInt(peerPort))
//                                .usePlaintext()
//                                .build();
                        ZabPeerServiceGrpc.ZabPeerServiceBlockingStub blockingStub = ZabPeerServiceGrpc.newBlockingStub(channel);
                        logger.format("Transaction proposed to " + peerPort);
                        // This call will block until a response (ACK) is received or an error occurs
                        blockingStub.proposeTransaction(updatedRequest);
                        // On success, increment ackCount
                        ackCount.incrementAndGet();
                        // Shutdown channel after use
                        channel.shutdown();
                    } catch (Exception e) {
                        System.err.println("Failed to propose transaction to peer " + peerPort + ": " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // Wait for all peer responses (or timeout after 5 seconds)
            try {
                boolean allResponded = latch.await(5, java.util.concurrent.TimeUnit.SECONDS);
                if (!allResponded) {
                    logger.format("Timeout occurred while waiting for ACKs. Proceeding with available ACKs.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.format("Interrupted while waiting for ACKs: " + e.getMessage());
            }
            // Shutdown the executor service
            executor.shutdown();

            // Check if quorum is reached
            if (ackCount.get() >= quorum) {
                logger.format("Received ACK from quorum (" + ackCount.get() + " out of " + totalNodes + ").");

                // Send commit message to all peers
                for (String peerPort : peersPortsList) {
                    try {
                        // Use the pre-initialized future stub if available
                        ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
                        if (stub == null) {
                            // Alternatively, create one if it was not already initialized

                            // local running
                            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", Integer.parseInt(peerPort))
                                    .usePlaintext()
                                    .build();

                            // docker
//                          ManagedChannel channel = ManagedChannelBuilder.forAddress("host.docker.internal", Integer.parseInt(peerPort))
//                                  .usePlaintext()
//                                  .build();
                            stub = ZabPeerServiceGrpc.newFutureStub(channel);
                            peerStubs.put(peerPort, stub);
                        }
                        CommitTransactionRequest commitTransactionRequest = CommitTransactionRequest
                                .newBuilder()
                                .setZxId(currentZxid)
                                .build();
                        stub.commitTransaction(commitTransactionRequest);
                    } catch (Exception e) {
                        System.err.println("Failed to commit transaction to peer " + peerPort + ": " + e.getMessage());
                    }
                }
                // Commit to the leader itself
                CommitTransactionRequest commitTransactionRequest = CommitTransactionRequest
                        .newBuilder()
                        .setZxId(currentZxid)
                        .build();
                this.commitTransaction(commitTransactionRequest, new CustomStreamObserver());
            } else {
                logger.format("Quorum not reached for proposed transaction. ACK count: " + ackCount.get() +
                        " (required quorum: " + quorum + ")");
            }

            done.onNext(Empty.getDefaultInstance());
            done.onCompleted();
        } else if (node.getState() == State.Following) {
            logger.format("Following node " + node.getId() +
                    " received proposed transaction " + request.getTransaction() + " with counter " +
                    request.getZxId().getCounter() + " and epoch " + request.getZxId().getEpoch());
            BankTransactionMap bankTransactionMap = node.getHistory().getProposed();
            History history = node.getHistory();
            currentZxid = ZxId.newBuilder()
                    .setEpoch(request.getZxId().getEpoch())
                    .setCounter(request.getZxId().getCounter())
                    .build();
            node.setHistory(history.toBuilder().setProposed(bankTransactionMap.toBuilder()
                    .addEntries(BankTransactionMapEntry
                            .newBuilder()
                            .setKey(currentZxid)
                            .setValue(request.getTransaction())
                            .build()).build()).build());
            done.onNext(Empty.getDefaultInstance());
            done.onCompleted();
        }
    }


    @Override
    public void commitTransaction(CommitTransactionRequest request, StreamObserver<Empty> done) {
        logger.format("Received commit transaction for zxId epoch "
                + request.getZxId().getEpoch() + " and counter " + request.getZxId().getCounter());
        if (node.getState() == State.Leading) {
//            for (String peerPort : peersPortsList) {
//                try {
//                    ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
//                    stub.commitTransaction(request);
//                    List<BankTransactionMapEntry> bankTransactionMapEntryList = node.getHistory().getProposed().getEntriesList();
//                    for (BankTransactionMapEntry bankTransactionMapEntry: bankTransactionMapEntryList) {
//                        if (bankTransactionMapEntry.getKey().getCounter() == request.getZxId().getCounter()
//                                && bankTransactionMapEntry.getKey().getEpoch() == request.getZxId().getEpoch()) {
//                            BankTransaction bankTransaction = bankTransactionMapEntry.getValue();
//                            logger.format("Transaction " + bankTransaction + " was committed.");
//                            BankTransactionMap committedTransactionMap = node.getHistory().getCommitted();
//                            node.setHistory(node.getHistory().toBuilder().setCommitted((committedTransactionMap.toBuilder()
//                                    .addEntries(BankTransactionMapEntry.newBuilder()
//                                            .setValue(bankTransaction)
//                                            .setKey(request.getZxId())
//                                            .build()))).build());
//                        }
//                    }
//                } catch (Exception e) {
//                    System.err.println("Failed to commit transaction to peer " + peerPort + ": " + e.getMessage());
//                }
//            }
            // Get proposed transaction by zxid and add it to committed transactions
            List<BankTransactionMapEntry> bankTransactionMapEntryList = node.getHistory().getProposed().getEntriesList();
            for (BankTransactionMapEntry bankTransactionMapEntry: bankTransactionMapEntryList) {
                if (bankTransactionMapEntry.getKey().getCounter() == request.getZxId().getCounter()
                        && bankTransactionMapEntry.getKey().getEpoch() == request.getZxId().getEpoch()) {
                    BankTransaction bankTransaction = bankTransactionMapEntry.getValue();
                    logger.format("Transaction " + bankTransaction + " was committed.");
                    BankTransactionMap committedTransactionMap = node.getHistory().getCommitted();
                    node.setHistory(node.getHistory().toBuilder().setCommitted((committedTransactionMap.toBuilder()
                            .addEntries(BankTransactionMapEntry.newBuilder()
                                    .setValue(bankTransaction)
                                    .setKey(request.getZxId())
                                    .build()))).build());
                }
            }
            done.onNext(Empty.getDefaultInstance());
            done.onNext(Empty.getDefaultInstance());
        } else if (node.getState() == State.Following) {
            // Get proposed transaction by zxid and add it to committed transactions
            List<BankTransactionMapEntry> bankTransactionMapEntryList = node.getHistory().getProposed().getEntriesList();
            for (BankTransactionMapEntry bankTransactionMapEntry: bankTransactionMapEntryList) {
                if (bankTransactionMapEntry.getKey().getCounter() == request.getZxId().getCounter()
                        && bankTransactionMapEntry.getKey().getEpoch() == request.getZxId().getEpoch()) {
                    BankTransaction bankTransaction = bankTransactionMapEntry.getValue();
                    logger.format("Transaction " + bankTransaction + " was committed.");
                    BankTransactionMap committedTransactionMap = node.getHistory().getCommitted();
                    node.setHistory(node.getHistory().toBuilder().setCommitted((committedTransactionMap.toBuilder()
                            .addEntries(BankTransactionMapEntry.newBuilder()
                                    .setValue(bankTransaction)
                                    .setKey(request.getZxId())
                                    .build()))).build());
                }
            }
            done.onNext(Empty.getDefaultInstance());
        }
    }

    @Override
    public void readBalances(BalanceRequest request, StreamObserver<BalanceResponse> done){
        int accountId = request.getId();
        int resultAmount = 0;

        // Sum up all the values from the transactions to this account
        for (BankTransactionMapEntry bankTransactionMapEntry: node.getHistory().getCommitted().getEntriesList()){
            if (bankTransactionMapEntry.getValue().getAccountId() == accountId){
                resultAmount += bankTransactionMapEntry.getValue().getAmount();
            }
        }
        done.onNext(BalanceResponse.newBuilder().setId(accountId).setAmount(resultAmount).build());
        done.onCompleted();
    }

    @Override
    public void updateHistoryOldThreshold(UpdateHistoryOldThresholdRequest request, StreamObserver<Empty> done) {
        logger.format("Updating old threshold to: " + request.getZxId());
        History history = node.getHistory();
        history.toBuilder().setOldThreshold(request.getZxId());
        node.setHistory(history);

        // Notify peers about the update
        for (String peerPort : peersPortsList) {
            try {
                ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
                stub.updateHistoryOldThreshold(request);
            } catch (Exception e) {
                System.err.println("Failed to update history old threshold for peer " + peerPort + ": " + e.getMessage());
            }
        }
        done.onNext(Empty.getDefaultInstance());
    }

    @Override
    public void sendHeartbeat(Empty request, StreamObserver<Empty> done) {
        logger.format("Sending heartbeat to all peers...");
        for (String peerPort : peersPortsList) {
            try {
                ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
                stub.sendHeartbeat(Empty.getDefaultInstance());
            } catch (Exception e) {
                System.err.println("Failed to send heartbeat to peer " + peerPort + ": " + e.getMessage());
            }
        }
        done.onNext(Empty.getDefaultInstance());
    }

    public List<String> getPeersPortsList() {
        return peersPortsList;
    }

    public void setPeersPortsList(List<String> peersPortsList) {
        this.peersPortsList = peersPortsList;
    }
}
