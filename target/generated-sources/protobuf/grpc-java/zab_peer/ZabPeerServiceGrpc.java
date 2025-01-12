package zab_peer;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.57.0)",
    comments = "Source: zab_peer.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ZabPeerServiceGrpc {

  private ZabPeerServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "zab_peer.ZabPeerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<zab_peer.ElectionRequest,
      com.google.protobuf.Empty> getSendElectionNotificationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendElectionNotification",
      requestType = zab_peer.ElectionRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<zab_peer.ElectionRequest,
      com.google.protobuf.Empty> getSendElectionNotificationMethod() {
    io.grpc.MethodDescriptor<zab_peer.ElectionRequest, com.google.protobuf.Empty> getSendElectionNotificationMethod;
    if ((getSendElectionNotificationMethod = ZabPeerServiceGrpc.getSendElectionNotificationMethod) == null) {
      synchronized (ZabPeerServiceGrpc.class) {
        if ((getSendElectionNotificationMethod = ZabPeerServiceGrpc.getSendElectionNotificationMethod) == null) {
          ZabPeerServiceGrpc.getSendElectionNotificationMethod = getSendElectionNotificationMethod =
              io.grpc.MethodDescriptor.<zab_peer.ElectionRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendElectionNotification"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_peer.ElectionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ZabPeerServiceMethodDescriptorSupplier("SendElectionNotification"))
              .build();
        }
      }
    }
    return getSendElectionNotificationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      zab_peer.GetStateResponse> getGetStateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetState",
      requestType = com.google.protobuf.Empty.class,
      responseType = zab_peer.GetStateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      zab_peer.GetStateResponse> getGetStateMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, zab_peer.GetStateResponse> getGetStateMethod;
    if ((getGetStateMethod = ZabPeerServiceGrpc.getGetStateMethod) == null) {
      synchronized (ZabPeerServiceGrpc.class) {
        if ((getGetStateMethod = ZabPeerServiceGrpc.getGetStateMethod) == null) {
          ZabPeerServiceGrpc.getGetStateMethod = getGetStateMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, zab_peer.GetStateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetState"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_peer.GetStateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ZabPeerServiceMethodDescriptorSupplier("GetState"))
              .build();
        }
      }
    }
    return getGetStateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<zab_peer.FollowerInfoRequest,
      zab_peer.FollowerInfoResponse> getSendFollowerInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendFollowerInfo",
      requestType = zab_peer.FollowerInfoRequest.class,
      responseType = zab_peer.FollowerInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<zab_peer.FollowerInfoRequest,
      zab_peer.FollowerInfoResponse> getSendFollowerInfoMethod() {
    io.grpc.MethodDescriptor<zab_peer.FollowerInfoRequest, zab_peer.FollowerInfoResponse> getSendFollowerInfoMethod;
    if ((getSendFollowerInfoMethod = ZabPeerServiceGrpc.getSendFollowerInfoMethod) == null) {
      synchronized (ZabPeerServiceGrpc.class) {
        if ((getSendFollowerInfoMethod = ZabPeerServiceGrpc.getSendFollowerInfoMethod) == null) {
          ZabPeerServiceGrpc.getSendFollowerInfoMethod = getSendFollowerInfoMethod =
              io.grpc.MethodDescriptor.<zab_peer.FollowerInfoRequest, zab_peer.FollowerInfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendFollowerInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_peer.FollowerInfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_peer.FollowerInfoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ZabPeerServiceMethodDescriptorSupplier("SendFollowerInfo"))
              .build();
        }
      }
    }
    return getSendFollowerInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.google.protobuf.Empty> getSendAckNewLeaderMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendAckNewLeader",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.google.protobuf.Empty> getSendAckNewLeaderMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.google.protobuf.Empty> getSendAckNewLeaderMethod;
    if ((getSendAckNewLeaderMethod = ZabPeerServiceGrpc.getSendAckNewLeaderMethod) == null) {
      synchronized (ZabPeerServiceGrpc.class) {
        if ((getSendAckNewLeaderMethod = ZabPeerServiceGrpc.getSendAckNewLeaderMethod) == null) {
          ZabPeerServiceGrpc.getSendAckNewLeaderMethod = getSendAckNewLeaderMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendAckNewLeader"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ZabPeerServiceMethodDescriptorSupplier("SendAckNewLeader"))
              .build();
        }
      }
    }
    return getSendAckNewLeaderMethod;
  }

  private static volatile io.grpc.MethodDescriptor<zab_peer.NewLeaderRequest,
      com.google.protobuf.Empty> getSendNewLeaderNotificationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendNewLeaderNotification",
      requestType = zab_peer.NewLeaderRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<zab_peer.NewLeaderRequest,
      com.google.protobuf.Empty> getSendNewLeaderNotificationMethod() {
    io.grpc.MethodDescriptor<zab_peer.NewLeaderRequest, com.google.protobuf.Empty> getSendNewLeaderNotificationMethod;
    if ((getSendNewLeaderNotificationMethod = ZabPeerServiceGrpc.getSendNewLeaderNotificationMethod) == null) {
      synchronized (ZabPeerServiceGrpc.class) {
        if ((getSendNewLeaderNotificationMethod = ZabPeerServiceGrpc.getSendNewLeaderNotificationMethod) == null) {
          ZabPeerServiceGrpc.getSendNewLeaderNotificationMethod = getSendNewLeaderNotificationMethod =
              io.grpc.MethodDescriptor.<zab_peer.NewLeaderRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendNewLeaderNotification"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_peer.NewLeaderRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ZabPeerServiceMethodDescriptorSupplier("SendNewLeaderNotification"))
              .build();
        }
      }
    }
    return getSendNewLeaderNotificationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<zab_peer.ProposeTransactionRequest,
      com.google.protobuf.Empty> getProposeTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ProposeTransaction",
      requestType = zab_peer.ProposeTransactionRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<zab_peer.ProposeTransactionRequest,
      com.google.protobuf.Empty> getProposeTransactionMethod() {
    io.grpc.MethodDescriptor<zab_peer.ProposeTransactionRequest, com.google.protobuf.Empty> getProposeTransactionMethod;
    if ((getProposeTransactionMethod = ZabPeerServiceGrpc.getProposeTransactionMethod) == null) {
      synchronized (ZabPeerServiceGrpc.class) {
        if ((getProposeTransactionMethod = ZabPeerServiceGrpc.getProposeTransactionMethod) == null) {
          ZabPeerServiceGrpc.getProposeTransactionMethod = getProposeTransactionMethod =
              io.grpc.MethodDescriptor.<zab_peer.ProposeTransactionRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ProposeTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_peer.ProposeTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ZabPeerServiceMethodDescriptorSupplier("ProposeTransaction"))
              .build();
        }
      }
    }
    return getProposeTransactionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<zab_peer.CommitTransactionRequest,
      com.google.protobuf.Empty> getCommitTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CommitTransaction",
      requestType = zab_peer.CommitTransactionRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<zab_peer.CommitTransactionRequest,
      com.google.protobuf.Empty> getCommitTransactionMethod() {
    io.grpc.MethodDescriptor<zab_peer.CommitTransactionRequest, com.google.protobuf.Empty> getCommitTransactionMethod;
    if ((getCommitTransactionMethod = ZabPeerServiceGrpc.getCommitTransactionMethod) == null) {
      synchronized (ZabPeerServiceGrpc.class) {
        if ((getCommitTransactionMethod = ZabPeerServiceGrpc.getCommitTransactionMethod) == null) {
          ZabPeerServiceGrpc.getCommitTransactionMethod = getCommitTransactionMethod =
              io.grpc.MethodDescriptor.<zab_peer.CommitTransactionRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CommitTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_peer.CommitTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ZabPeerServiceMethodDescriptorSupplier("CommitTransaction"))
              .build();
        }
      }
    }
    return getCommitTransactionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<zab_peer.BalanceRequest,
      zab_peer.BalanceResponse> getReadBalancesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReadBalances",
      requestType = zab_peer.BalanceRequest.class,
      responseType = zab_peer.BalanceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<zab_peer.BalanceRequest,
      zab_peer.BalanceResponse> getReadBalancesMethod() {
    io.grpc.MethodDescriptor<zab_peer.BalanceRequest, zab_peer.BalanceResponse> getReadBalancesMethod;
    if ((getReadBalancesMethod = ZabPeerServiceGrpc.getReadBalancesMethod) == null) {
      synchronized (ZabPeerServiceGrpc.class) {
        if ((getReadBalancesMethod = ZabPeerServiceGrpc.getReadBalancesMethod) == null) {
          ZabPeerServiceGrpc.getReadBalancesMethod = getReadBalancesMethod =
              io.grpc.MethodDescriptor.<zab_peer.BalanceRequest, zab_peer.BalanceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReadBalances"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_peer.BalanceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_peer.BalanceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ZabPeerServiceMethodDescriptorSupplier("ReadBalances"))
              .build();
        }
      }
    }
    return getReadBalancesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<zab_peer.UpdateHistoryOldThresholdRequest,
      com.google.protobuf.Empty> getUpdateHistoryOldThresholdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateHistoryOldThreshold",
      requestType = zab_peer.UpdateHistoryOldThresholdRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<zab_peer.UpdateHistoryOldThresholdRequest,
      com.google.protobuf.Empty> getUpdateHistoryOldThresholdMethod() {
    io.grpc.MethodDescriptor<zab_peer.UpdateHistoryOldThresholdRequest, com.google.protobuf.Empty> getUpdateHistoryOldThresholdMethod;
    if ((getUpdateHistoryOldThresholdMethod = ZabPeerServiceGrpc.getUpdateHistoryOldThresholdMethod) == null) {
      synchronized (ZabPeerServiceGrpc.class) {
        if ((getUpdateHistoryOldThresholdMethod = ZabPeerServiceGrpc.getUpdateHistoryOldThresholdMethod) == null) {
          ZabPeerServiceGrpc.getUpdateHistoryOldThresholdMethod = getUpdateHistoryOldThresholdMethod =
              io.grpc.MethodDescriptor.<zab_peer.UpdateHistoryOldThresholdRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateHistoryOldThreshold"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_peer.UpdateHistoryOldThresholdRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ZabPeerServiceMethodDescriptorSupplier("UpdateHistoryOldThreshold"))
              .build();
        }
      }
    }
    return getUpdateHistoryOldThresholdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.google.protobuf.Empty> getSendHeartbeatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendHeartbeat",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.google.protobuf.Empty> getSendHeartbeatMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.google.protobuf.Empty> getSendHeartbeatMethod;
    if ((getSendHeartbeatMethod = ZabPeerServiceGrpc.getSendHeartbeatMethod) == null) {
      synchronized (ZabPeerServiceGrpc.class) {
        if ((getSendHeartbeatMethod = ZabPeerServiceGrpc.getSendHeartbeatMethod) == null) {
          ZabPeerServiceGrpc.getSendHeartbeatMethod = getSendHeartbeatMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendHeartbeat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ZabPeerServiceMethodDescriptorSupplier("SendHeartbeat"))
              .build();
        }
      }
    }
    return getSendHeartbeatMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ZabPeerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZabPeerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZabPeerServiceStub>() {
        @java.lang.Override
        public ZabPeerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZabPeerServiceStub(channel, callOptions);
        }
      };
    return ZabPeerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ZabPeerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZabPeerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZabPeerServiceBlockingStub>() {
        @java.lang.Override
        public ZabPeerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZabPeerServiceBlockingStub(channel, callOptions);
        }
      };
    return ZabPeerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ZabPeerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZabPeerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZabPeerServiceFutureStub>() {
        @java.lang.Override
        public ZabPeerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZabPeerServiceFutureStub(channel, callOptions);
        }
      };
    return ZabPeerServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void sendElectionNotification(zab_peer.ElectionRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendElectionNotificationMethod(), responseObserver);
    }

    /**
     */
    default void getState(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<zab_peer.GetStateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStateMethod(), responseObserver);
    }

    /**
     */
    default void sendFollowerInfo(zab_peer.FollowerInfoRequest request,
        io.grpc.stub.StreamObserver<zab_peer.FollowerInfoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendFollowerInfoMethod(), responseObserver);
    }

    /**
     */
    default void sendAckNewLeader(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendAckNewLeaderMethod(), responseObserver);
    }

    /**
     */
    default void sendNewLeaderNotification(zab_peer.NewLeaderRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendNewLeaderNotificationMethod(), responseObserver);
    }

    /**
     */
    default void proposeTransaction(zab_peer.ProposeTransactionRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getProposeTransactionMethod(), responseObserver);
    }

    /**
     */
    default void commitTransaction(zab_peer.CommitTransactionRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCommitTransactionMethod(), responseObserver);
    }

    /**
     */
    default void readBalances(zab_peer.BalanceRequest request,
        io.grpc.stub.StreamObserver<zab_peer.BalanceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadBalancesMethod(), responseObserver);
    }

    /**
     */
    default void updateHistoryOldThreshold(zab_peer.UpdateHistoryOldThresholdRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateHistoryOldThresholdMethod(), responseObserver);
    }

    /**
     */
    default void sendHeartbeat(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendHeartbeatMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ZabPeerService.
   */
  public static abstract class ZabPeerServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ZabPeerServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ZabPeerService.
   */
  public static final class ZabPeerServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ZabPeerServiceStub> {
    private ZabPeerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ZabPeerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZabPeerServiceStub(channel, callOptions);
    }

    /**
     */
    public void sendElectionNotification(zab_peer.ElectionRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendElectionNotificationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getState(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<zab_peer.GetStateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendFollowerInfo(zab_peer.FollowerInfoRequest request,
        io.grpc.stub.StreamObserver<zab_peer.FollowerInfoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendFollowerInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendAckNewLeader(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendAckNewLeaderMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendNewLeaderNotification(zab_peer.NewLeaderRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendNewLeaderNotificationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void proposeTransaction(zab_peer.ProposeTransactionRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getProposeTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void commitTransaction(zab_peer.CommitTransactionRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCommitTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void readBalances(zab_peer.BalanceRequest request,
        io.grpc.stub.StreamObserver<zab_peer.BalanceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadBalancesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateHistoryOldThreshold(zab_peer.UpdateHistoryOldThresholdRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateHistoryOldThresholdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendHeartbeat(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendHeartbeatMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ZabPeerService.
   */
  public static final class ZabPeerServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ZabPeerServiceBlockingStub> {
    private ZabPeerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ZabPeerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZabPeerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty sendElectionNotification(zab_peer.ElectionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendElectionNotificationMethod(), getCallOptions(), request);
    }

    /**
     */
    public zab_peer.GetStateResponse getState(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStateMethod(), getCallOptions(), request);
    }

    /**
     */
    public zab_peer.FollowerInfoResponse sendFollowerInfo(zab_peer.FollowerInfoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendFollowerInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty sendAckNewLeader(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendAckNewLeaderMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty sendNewLeaderNotification(zab_peer.NewLeaderRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendNewLeaderNotificationMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty proposeTransaction(zab_peer.ProposeTransactionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getProposeTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty commitTransaction(zab_peer.CommitTransactionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCommitTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public zab_peer.BalanceResponse readBalances(zab_peer.BalanceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadBalancesMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty updateHistoryOldThreshold(zab_peer.UpdateHistoryOldThresholdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateHistoryOldThresholdMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty sendHeartbeat(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendHeartbeatMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ZabPeerService.
   */
  public static final class ZabPeerServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ZabPeerServiceFutureStub> {
    private ZabPeerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ZabPeerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZabPeerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> sendElectionNotification(
        zab_peer.ElectionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendElectionNotificationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<zab_peer.GetStateResponse> getState(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<zab_peer.FollowerInfoResponse> sendFollowerInfo(
        zab_peer.FollowerInfoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendFollowerInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> sendAckNewLeader(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendAckNewLeaderMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> sendNewLeaderNotification(
        zab_peer.NewLeaderRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendNewLeaderNotificationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> proposeTransaction(
        zab_peer.ProposeTransactionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getProposeTransactionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> commitTransaction(
        zab_peer.CommitTransactionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCommitTransactionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<zab_peer.BalanceResponse> readBalances(
        zab_peer.BalanceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadBalancesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> updateHistoryOldThreshold(
        zab_peer.UpdateHistoryOldThresholdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateHistoryOldThresholdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> sendHeartbeat(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendHeartbeatMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_ELECTION_NOTIFICATION = 0;
  private static final int METHODID_GET_STATE = 1;
  private static final int METHODID_SEND_FOLLOWER_INFO = 2;
  private static final int METHODID_SEND_ACK_NEW_LEADER = 3;
  private static final int METHODID_SEND_NEW_LEADER_NOTIFICATION = 4;
  private static final int METHODID_PROPOSE_TRANSACTION = 5;
  private static final int METHODID_COMMIT_TRANSACTION = 6;
  private static final int METHODID_READ_BALANCES = 7;
  private static final int METHODID_UPDATE_HISTORY_OLD_THRESHOLD = 8;
  private static final int METHODID_SEND_HEARTBEAT = 9;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_ELECTION_NOTIFICATION:
          serviceImpl.sendElectionNotification((zab_peer.ElectionRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_GET_STATE:
          serviceImpl.getState((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<zab_peer.GetStateResponse>) responseObserver);
          break;
        case METHODID_SEND_FOLLOWER_INFO:
          serviceImpl.sendFollowerInfo((zab_peer.FollowerInfoRequest) request,
              (io.grpc.stub.StreamObserver<zab_peer.FollowerInfoResponse>) responseObserver);
          break;
        case METHODID_SEND_ACK_NEW_LEADER:
          serviceImpl.sendAckNewLeader((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_SEND_NEW_LEADER_NOTIFICATION:
          serviceImpl.sendNewLeaderNotification((zab_peer.NewLeaderRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_PROPOSE_TRANSACTION:
          serviceImpl.proposeTransaction((zab_peer.ProposeTransactionRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_COMMIT_TRANSACTION:
          serviceImpl.commitTransaction((zab_peer.CommitTransactionRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_READ_BALANCES:
          serviceImpl.readBalances((zab_peer.BalanceRequest) request,
              (io.grpc.stub.StreamObserver<zab_peer.BalanceResponse>) responseObserver);
          break;
        case METHODID_UPDATE_HISTORY_OLD_THRESHOLD:
          serviceImpl.updateHistoryOldThreshold((zab_peer.UpdateHistoryOldThresholdRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_SEND_HEARTBEAT:
          serviceImpl.sendHeartbeat((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSendElectionNotificationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              zab_peer.ElectionRequest,
              com.google.protobuf.Empty>(
                service, METHODID_SEND_ELECTION_NOTIFICATION)))
        .addMethod(
          getGetStateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              zab_peer.GetStateResponse>(
                service, METHODID_GET_STATE)))
        .addMethod(
          getSendFollowerInfoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              zab_peer.FollowerInfoRequest,
              zab_peer.FollowerInfoResponse>(
                service, METHODID_SEND_FOLLOWER_INFO)))
        .addMethod(
          getSendAckNewLeaderMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              com.google.protobuf.Empty>(
                service, METHODID_SEND_ACK_NEW_LEADER)))
        .addMethod(
          getSendNewLeaderNotificationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              zab_peer.NewLeaderRequest,
              com.google.protobuf.Empty>(
                service, METHODID_SEND_NEW_LEADER_NOTIFICATION)))
        .addMethod(
          getProposeTransactionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              zab_peer.ProposeTransactionRequest,
              com.google.protobuf.Empty>(
                service, METHODID_PROPOSE_TRANSACTION)))
        .addMethod(
          getCommitTransactionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              zab_peer.CommitTransactionRequest,
              com.google.protobuf.Empty>(
                service, METHODID_COMMIT_TRANSACTION)))
        .addMethod(
          getReadBalancesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              zab_peer.BalanceRequest,
              zab_peer.BalanceResponse>(
                service, METHODID_READ_BALANCES)))
        .addMethod(
          getUpdateHistoryOldThresholdMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              zab_peer.UpdateHistoryOldThresholdRequest,
              com.google.protobuf.Empty>(
                service, METHODID_UPDATE_HISTORY_OLD_THRESHOLD)))
        .addMethod(
          getSendHeartbeatMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              com.google.protobuf.Empty>(
                service, METHODID_SEND_HEARTBEAT)))
        .build();
  }

  private static abstract class ZabPeerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ZabPeerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return zab_peer.ZabPeer.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ZabPeerService");
    }
  }

  private static final class ZabPeerServiceFileDescriptorSupplier
      extends ZabPeerServiceBaseDescriptorSupplier {
    ZabPeerServiceFileDescriptorSupplier() {}
  }

  private static final class ZabPeerServiceMethodDescriptorSupplier
      extends ZabPeerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ZabPeerServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ZabPeerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ZabPeerServiceFileDescriptorSupplier())
              .addMethod(getSendElectionNotificationMethod())
              .addMethod(getGetStateMethod())
              .addMethod(getSendFollowerInfoMethod())
              .addMethod(getSendAckNewLeaderMethod())
              .addMethod(getSendNewLeaderNotificationMethod())
              .addMethod(getProposeTransactionMethod())
              .addMethod(getCommitTransactionMethod())
              .addMethod(getReadBalancesMethod())
              .addMethod(getUpdateHistoryOldThresholdMethod())
              .addMethod(getSendHeartbeatMethod())
              .build();
        }
      }
    }
    return result;
  }
}
