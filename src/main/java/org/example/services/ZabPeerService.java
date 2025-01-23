package org.example.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.entities.CustomStreamObserver;
import org.example.entities.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zab.*;
import zab_peer.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@GrpcService
public class ZabPeerService extends ZabPeerServiceGrpc.ZabPeerServiceImplBase{

    @Autowired
    public Node node;

    private List<String> peersPortsList;
    private ZabPeerServiceGrpc.ZabPeerServiceStub stub;
    private Map<String, ZabPeerServiceGrpc.ZabPeerServiceFutureStub> peerStubs = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<ElectionRequest> voteQueue = new ConcurrentLinkedQueue<>();
    private ExecutorService voteProcessingExecutor = Executors.newSingleThreadExecutor();
    private ExecutorService voteSendingExecutor = Executors.newCachedThreadPool();
    private AtomicBoolean electionInProgress = new AtomicBoolean(false);

    private int currentEpoch = 0;
    private Vote currentVote;
    private ConcurrentHashMap<Integer, Integer> voteCounts = new ConcurrentHashMap<>();
    private long electionStartTime;
    private static final long ELECTION_TIMEOUT_MILLIS = 5000;

    // Initialize the stubs for peer communication
    public ZabPeerService() {
        this.peersPortsList = new ArrayList<>();
    }

    public void logNodeData(){
        System.out.println("Node's id is " + node.getId());
        System.out.println("Node's port is " + node.getPort());
    }

    public void initializePeers() {
        this.peersPortsList = node.getPeerPorts();

        if (this.voteCounts == null){
            this.voteCounts = new ConcurrentHashMap<>();
        }

        // Create stubs for each peer
        for (String peerPort : peersPortsList) {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", Integer.parseInt(peerPort))
                    .usePlaintext()
                    .build();
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

            // Process the vote
            Vote incomingVote = request.getVote();
            System.out.println("Processing vote from Node " + incomingVote.getId());

            // Check if the incoming vote is preferred
            if (isPreferredVote(incomingVote)) {
                System.out.println("Received vote is preferred above the current for node " + node.getId());
                System.out.println("Updating current vote for Node " + node.getId());
                // Retract previous values
//                this.voteCounts.computeIfPresent(this.currentVote.getId(), (key, count) -> count > 0 ? count - 1 : 0);

                this.currentVote = incomingVote;
                // Count new vote
                this.voteCounts.compute(incomingVote.getId(), (key, count) -> (count == null) ? 1 : count + 1);

                System.out.println("Current votes: " + voteCounts);

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
                concludeElection();
            }

             // Check if timeout has expired
            if (System.currentTimeMillis() - electionStartTime > ELECTION_TIMEOUT_MILLIS) {
                System.out.println("Election timed out. Retrying...");
                System.out.println(this.voteCounts);
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

    private void concludeElection() {
        System.out.println("Quorum reached! Node " + node.getId() + " becomes the leader.");
        electionInProgress.set(false);
        node.setState(State.Leading);

        // Notify all followers about the new leader
        for (String peerPort : peersPortsList) {
            try {
                ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
                stub.sendNewLeaderNotification(NewLeaderRequest.newBuilder().setId(node.getId()).build());
            } catch (Exception e) {
                System.err.println("Failed to notify peer " + peerPort + ": " + e.getMessage());
            }
        }
    }

    private void broadcastToPeers(ElectionRequest request, StreamObserver<Empty> done) {
        System.out.println("broadcastToPeers");
        for (String peerPort : peersPortsList) {
            try {
                ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
                System.out.println("Sending vote from node " + node.getId() + " to peer on port " + peerPort + " for node " + request.getVote().getId());
                stub.sendElectionNotification(request);
            } catch (Exception e) {
                System.err.println("Failed to send election notification to peer " + peerPort + ": " + e.getMessage());
            }
        }
    }

    private void sendFirstElectionNotification(long currentTime, ElectionRequest request, StreamObserver<Empty> done){
        System.out.println("sendFirstElectionNotification");
        // Start a new election if none is ongoing
        if (node.getState() != State.Election || currentTime - this.electionStartTime > ELECTION_TIMEOUT_MILLIS) {
            this.electionInProgress.set(true);
            this.electionStartTime = currentTime;
            this.currentEpoch += 1;
            this.node.setState(State.Election);

            // Reset vote counts and vote for itself
            System.out.println("Resetting votes count...");
            this.voteCounts.clear();
            this.voteCounts.put(node.getId(), 1);
            System.out.println(this.voteCounts);

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
        System.out.println("sendElectionNotification");
        long currentTime = System.currentTimeMillis();

        // Enqueue the incoming vote for processing
        voteQueue.offer(request);

        // If no ongoing election, start one
        if (!electionInProgress.get()) {
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
            System.out.println("Node " + node.getId() + " is in Election state. Waiting for election to complete.");

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
                    System.out.println("Election timeout occurred. Proceeding with current state.");
                    System.out.println(this.voteCounts);
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
        System.out.println("State of the " + node.getId() + " node is " + node.getState().name());

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
                    System.out.println("Sent follower's zxId: " + request.getLastZxId() + " to port " + peerPort);
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
            System.out.println("Sending ACK for new leader...");
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

    /** To propose AND commit transaction enough to call only proposeTransaction method in client. */
    @Override
    public void proposeTransaction(ProposeTransactionRequest request, StreamObserver<Empty> done) {
        // Start FLE if it wasn't started yet
        if (node.getState() != State.Leading || node.getState() != State.Following){
            this.sendElectionNotification(ElectionRequest.newBuilder().setId(node.getId()).build(), new CustomStreamObserver());
        }

        System.out.println("Broadcasting proposed transaction: " + request.getTransaction());
        if (node.getState() == State.Leading) {
            for (String peerPort : peersPortsList) {
                try {
                    ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
                    stub.proposeTransaction(request);
                } catch (Exception e) {
                    System.err.println("Failed to propose transaction to peer " + peerPort + ": " + e.getMessage());
                }
            }
            // Upon receiving ACK from all followers -- send COMMIT to all followers
            for (String peerPort : peersPortsList) {
                try {
                    ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
                    CommitTransactionRequest commitTransactionRequest = CommitTransactionRequest
                            .newBuilder()
                            .setZxId(request.getZxId())
                            .build();
                    stub.commitTransaction(commitTransactionRequest);
                } catch (Exception e) {
                    System.err.println("Failed to propose transaction to peer " + peerPort + ": " + e.getMessage());
                }
            }
            done.onNext(Empty.getDefaultInstance());
        } else if (node.getState() == State.Following) {
            // Add transaction to proposed list
            BankTransactionMap bankTransactionMap = node.getHistory().getProposed();
            bankTransactionMap.getEntriesList()
                    .add(BankTransactionMapEntry
                            .newBuilder()
                            .setKey(request.getZxId())
                            .setValue(request.getTransaction())
                            .build());
            node.setHistory(node.getHistory().toBuilder().setProposed(bankTransactionMap).build());
            done.onNext(Empty.getDefaultInstance()); // ACK
        }
    }

    @Override
    public void commitTransaction(CommitTransactionRequest request, StreamObserver<Empty> done) {
        System.out.println("Broadcasting commit transaction for zxId: " + request.getZxId());
        if (node.getState() == State.Leading) {
            for (String peerPort : peersPortsList) {
                try {
                    ZabPeerServiceGrpc.ZabPeerServiceFutureStub stub = peerStubs.get(peerPort);
                    stub.commitTransaction(request);
                } catch (Exception e) {
                    System.err.println("Failed to commit transaction to peer " + peerPort + ": " + e.getMessage());
                }
            }
            done.onNext(Empty.getDefaultInstance());
        } else if (node.getState() == State.Following) {
            // Get proposed transaction by zxid and add it to committed transactions
            List<BankTransactionMapEntry> bankTransactionMapEntryList = node.getHistory().getProposed().getEntriesList();
            for (BankTransactionMapEntry bankTransactionMapEntry: bankTransactionMapEntryList) {
                if (bankTransactionMapEntry.getKey().getCounter() == request.getZxId().getCounter()
                        && bankTransactionMapEntry.getKey().getEpoch() == request.getZxId().getEpoch()) {
                    BankTransaction bankTransaction = bankTransactionMapEntry.getValue();
                    BankTransactionMap committedTransactionMap = node.getHistory().getCommitted();
                    committedTransactionMap.toBuilder()
                            .addEntries(BankTransactionMapEntry.newBuilder()
                                    .setValue(bankTransaction)
                                    .setKey(request.getZxId())
                                    .build());
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
    }

    @Override
    public void updateHistoryOldThreshold(UpdateHistoryOldThresholdRequest request, StreamObserver<Empty> done) {
        System.out.println("Updating old threshold to: " + request.getZxId());
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
        System.out.println("Sending heartbeat to all peers...");
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
