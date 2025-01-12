package org.example.services;

import com.google.protobuf.Empty;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.entities.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zab.*;
import zab_peer.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZabPeerService {

    @Autowired
    private Node node;

    @Autowired
    private ArrayList<String> peersPortsList;

    private ZabPeerServiceGrpc.ZabPeerServiceStub stub;

    private Map<String, ZabPeerServiceGrpc.ZabPeerServiceBlockingStub> peerStubs = new HashMap<>();
    private Map<Integer, Integer> voteCounts = new HashMap<>();
    private final Object electionLock = new Object();

    private long electionTimeoutMillis = 5000; // Timeout in milliseconds
    private long electionStartTime;

    // Initialize the stubs for peer communication
    public ZabPeerService() {
        this.peersPortsList = new ArrayList<>();
    }

    public void initializePeers(ArrayList<String> peersPortsList) {
        this.peersPortsList = peersPortsList;

        // Create stubs for each peer
        for (String peerPort : peersPortsList) {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", Integer.parseInt(peerPort))
                    .usePlaintext()
                    .build();
            ZabPeerServiceGrpc.ZabPeerServiceBlockingStub stub = ZabPeerServiceGrpc.newBlockingStub(channel);
            peerStubs.put(peerPort, stub);
        }
    }

    private void broadcastToPeers(ElectionRequest request) {
        for (String peerPort : peersPortsList) {
            try {
                ZabPeerServiceGrpc.ZabPeerServiceBlockingStub stub = peerStubs.get(peerPort);
                stub.sendElectionNotification(request);
            } catch (Exception e) {
                System.err.println("Failed to send election notification to peer " + peerPort + ": " + e.getMessage());
            }
        }
    }

    private void sendFirstElectionNotification(long currentTime, ElectionRequest request, RpcCallback<Empty> done){
        // Start a new election if none is ongoing
        if (node.getState() != State.Election || currentTime - electionStartTime > electionTimeoutMillis) {
            System.out.println("Starting new election...");
            electionStartTime = currentTime;
            node.setState(State.Election);

            // Reset vote counts and vote for itself
            voteCounts.clear();
            voteCounts.put(node.getId(), 1);

            // Broadcast initial election notification
            ElectionRequest initialRequest = ElectionRequest.newBuilder()
                    .setId(node.getId())
                    .setVote(Vote.newBuilder()
                            .setId(node.getId())
                            .setLastZxId(node.getHistory().getLastCommitedZxId())
                            .build())
                    .setState(State.Election)
                    .setRound(1)
                    .build();
            broadcastToPeers(initialRequest);
        }
    }

    public void sendElectionNotification(RpcController controller, ElectionRequest request, RpcCallback<Empty> done) {
        synchronized (electionLock) {
            long currentTime = System.currentTimeMillis();

            // Start a new election if none is ongoing
            sendFirstElectionNotification(currentTime, request, done);

            // Process incoming election request
            Vote incomingVote = request.getVote();
            System.out.println("Received vote from Node " + incomingVote.getId());

            if (isPreferredVote(incomingVote)) {
                // Update node's vote
                node.setId(incomingVote.getId());
                node.setState(State.Election);

                // Count vote
                voteCounts.put(incomingVote.getId(), voteCounts.getOrDefault(incomingVote.getId(), 0) + 1);

                // Broadcast updated election notification
                ElectionRequest updatedRequest = ElectionRequest.newBuilder()
                        .setId(node.getId())
                        .setVote(incomingVote)
                        .setState(State.Election)
                        .build();
                broadcastToPeers(updatedRequest);
            }

            // Check if a quorum has been reached
            if (isQuorumReached()) {
                System.out.println("Quorum reached! Node " + node.getId() + " becomes the leader.");
                node.setState(State.Leading);
                // Notify all followers about new leader and update its statuses
                for (String peerPort : peersPortsList) {
                    try {
                        ZabPeerServiceGrpc.ZabPeerServiceBlockingStub stub = peerStubs.get(peerPort);
                        stub.sendNewLeaderNotification(NewLeaderRequest.newBuilder().setId(node.getId()).build());
                    } catch (Exception e) {
                        System.err.println("Failed to send new leader notification to peer " + peerPort + ": " + e.getMessage());
                    }
                }
                done.run(Empty.getDefaultInstance());
                return;
            }

            // Check if timeout has expired
            if (currentTime - electionStartTime > electionTimeoutMillis) {
                System.out.println("Election timed out. Retrying...");
                node.setState(State.Election);
                sendElectionNotification(controller, request, done);
                return;
            }
        }
        done.run(Empty.getDefaultInstance());
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

    public void getState(RpcController controller, Empty request, RpcCallback<GetStateResponse> done) {
        // Provide the current state of this node
        GetStateResponse response = GetStateResponse.newBuilder()
                .setState(node.getState())
                .build();
        done.run(response);
    }

    public void sendFollowerInfo(RpcController controller, FollowerInfoRequest request, RpcCallback<FollowerInfoResponse> done) {

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
            done.run(followerInfoResponseAck);

            //TODO: overload <>= for zxid
            if (request.getLastZxId().getCounter() <= node.getHistory().getLastCommitedZxId().getCounter()) {
                if (request.getLastZxId().getCounter() < node.getHistory().getOldThreshold().getCounter()) {
                    Snap snap = Snap.newBuilder().setHistory(node.getHistory()).build();
                    FollowerInfoResponse response = FollowerInfoResponse.newBuilder().setSnap(snap).build();
                    done.run(response);
                } else {
                    // TODO: filter transactions
                    Diff diff = Diff.newBuilder().setTransactions(node.getHistory().getCommitted()).build();
                    FollowerInfoResponse response = FollowerInfoResponse.newBuilder().setDiff(diff).build();
                    done.run(response);
                }
            } else {
                Trunc trunc = Trunc.newBuilder().setLastCommitedZxId(node.getHistory().getLastCommitedZxId()).build();
                FollowerInfoResponse response = FollowerInfoResponse.newBuilder().setTrunc(trunc).build();
                done.run(response);
            }

            FollowerInfoResponse response = FollowerInfoResponse.newBuilder()
                    .setLastZxId(node.getHistory().getLastCommitedZxId())
                    .build();
            done.run(response);
        }
    }

    public void sendFollowerInfoRequest(RpcController controller, FollowerInfoResponse response, RpcCallback<FollowerInfoRequest> done) {
        if (node.getState() == State.Following){

            for (String peerPort : peersPortsList) {
                try {
                    ZabPeerServiceGrpc.ZabPeerServiceBlockingStub stub = peerStubs.get(peerPort);
                    FollowerInfoRequest request = FollowerInfoRequest.newBuilder()
                            .setLastZxId(node.getHistory().getLastCommitedZxId())
                            .build();
                    FollowerInfoResponse followerInfoResponse = stub.sendFollowerInfo(request);
                    System.out.println("Sent follower's zxId: " + request.getLastZxId() + " to port " + peerPort);
                    if (followerInfoResponse.getLastZxId().getCounter() < node.getHistory().getLastCommitedZxId().getCounter()) {
                        node.setState(State.Election);
                        this.sendElectionNotification(controller, ElectionRequest.newBuilder().setId(node.getId()).build(), (RpcCallback<Empty>) new Object());
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
                    ZabPeerServiceGrpc.ZabPeerServiceBlockingStub stub = peerStubs.get(peerPort);
                    NewLeaderRequest newLeaderRequest = NewLeaderRequest.newBuilder().setId(node.getId()).build();
                    stub.sendNewLeaderNotification(newLeaderRequest);
                } catch (Exception e) {
                    System.err.println("Failed to send new leader notification to peer " + peerPort + ": " + e.getMessage());
                }
            }
        }
    }

    public void sendNewLeaderNotification(RpcController controller, NewLeaderRequest request, RpcCallback<Empty> done){
        // Upon receiving notification set new leader to the node
        if (node.getState() != State.Leading){
            node.setState(State.Following);
            node.setLeaderId(request.getId());
            done.run(Empty.getDefaultInstance());
        }
    }

    public void sendAckNewLeader(RpcController controller, Empty request, RpcCallback<Empty> done) {
        if (node.getState() == State.Following){
            System.out.println("Sending ACK for new leader...");
            for (String peerPort : peersPortsList) {
                try {
                    ZabPeerServiceGrpc.ZabPeerServiceBlockingStub stub = peerStubs.get(peerPort);
                    stub.sendAckNewLeader(Empty.getDefaultInstance());
                } catch (Exception e) {
                    System.err.println("Failed to send ACK to peer " + peerPort + ": " + e.getMessage());
                }
            }
            done.run(Empty.getDefaultInstance());
        } else if (node.getState() == State.Leading) {
            done.run(request);
        }
    }

    /** To propose AND commit transaction enough to call only proposeTransaction method in client. */
    public void proposeTransaction(RpcController controller, ProposeTransactionRequest request, RpcCallback<Empty> done) {
        // Start FLE if it wasn't started yet
        if (node.getState() != State.Leading || node.getState() != State.Following){
            this.sendElectionNotification(controller, ElectionRequest.newBuilder().setId(node.getId()).build(), (RpcCallback<Empty>) new Object());
        }

        System.out.println("Broadcasting proposed transaction: " + request.getTransaction());
        if (node.getState() == State.Leading) {
            for (String peerPort : peersPortsList) {
                try {
                    ZabPeerServiceGrpc.ZabPeerServiceBlockingStub stub = peerStubs.get(peerPort);
                    stub.proposeTransaction(request);
                } catch (Exception e) {
                    System.err.println("Failed to propose transaction to peer " + peerPort + ": " + e.getMessage());
                }
            }
            // Upon receiving ACK from all followers -- send COMMIT to all followers
            for (String peerPort : peersPortsList) {
                try {
                    ZabPeerServiceGrpc.ZabPeerServiceBlockingStub stub = peerStubs.get(peerPort);
                    CommitTransactionRequest commitTransactionRequest = CommitTransactionRequest
                            .newBuilder()
                            .setZxId(request.getZxId())
                            .build();
                    stub.commitTransaction(commitTransactionRequest);
                } catch (Exception e) {
                    System.err.println("Failed to propose transaction to peer " + peerPort + ": " + e.getMessage());
                }
            }
            done.run(Empty.getDefaultInstance());
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
            done.run(Empty.getDefaultInstance()); // ACK
        }
    }

    public void commitTransaction(RpcController controller, CommitTransactionRequest request, RpcCallback<Empty> done) {
        System.out.println("Broadcasting commit transaction for zxId: " + request.getZxId());
        if (node.getState() == State.Leading) {
            for (String peerPort : peersPortsList) {
                try {
                    ZabPeerServiceGrpc.ZabPeerServiceBlockingStub stub = peerStubs.get(peerPort);
                    stub.commitTransaction(request);
                } catch (Exception e) {
                    System.err.println("Failed to commit transaction to peer " + peerPort + ": " + e.getMessage());
                }
            }
            done.run(Empty.getDefaultInstance());
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
            done.run(Empty.getDefaultInstance());
        }
    }

    public void readBalances(RpcController controller, BalanceRequest request, RpcCallback<BalanceResponse> done){
        int accountId = request.getId();
        int resultAmount = 0;

        // Sum up all the values from the transactions to this account
        for (BankTransactionMapEntry bankTransactionMapEntry: node.getHistory().getCommitted().getEntriesList()){
            if (bankTransactionMapEntry.getValue().getAccountId() == accountId){
                resultAmount += bankTransactionMapEntry.getValue().getAmount();
            }
        }
        done.run(BalanceResponse.newBuilder().setId(accountId).setAmount(resultAmount).build());
    }

    public void updateHistoryOldThreshold(RpcController controller, UpdateHistoryOldThresholdRequest request, RpcCallback<Empty> done) {
        System.out.println("Updating old threshold to: " + request.getZxId());
        History history = node.getHistory();
        history.toBuilder().setOldThreshold(request.getZxId());
        node.setHistory(history);

        // Notify peers about the update
        for (String peerPort : peersPortsList) {
            try {
                ZabPeerServiceGrpc.ZabPeerServiceBlockingStub stub = peerStubs.get(peerPort);
                stub.updateHistoryOldThreshold(request);
            } catch (Exception e) {
                System.err.println("Failed to update history old threshold for peer " + peerPort + ": " + e.getMessage());
            }
        }
        done.run(Empty.getDefaultInstance());
    }

    public void sendHeartbeat(RpcController controller, Empty request, RpcCallback<Empty> done) {
        System.out.println("Sending heartbeat to all peers...");
        for (String peerPort : peersPortsList) {
            try {
                ZabPeerServiceGrpc.ZabPeerServiceBlockingStub stub = peerStubs.get(peerPort);
                stub.sendHeartbeat(Empty.getDefaultInstance());
            } catch (Exception e) {
                System.err.println("Failed to send heartbeat to peer " + peerPort + ": " + e.getMessage());
            }
        }
        done.run(Empty.getDefaultInstance());
    }
}
