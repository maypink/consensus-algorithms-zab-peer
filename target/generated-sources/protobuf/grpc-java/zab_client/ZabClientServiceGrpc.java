package zab_client;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.57.0)",
    comments = "Source: zab_client.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ZabClientServiceGrpc {

  private ZabClientServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "zab_client.ZabClientService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<zab_client.WriteTransactionRequest,
      com.google.protobuf.Empty> getWriteTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "WriteTransaction",
      requestType = zab_client.WriteTransactionRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<zab_client.WriteTransactionRequest,
      com.google.protobuf.Empty> getWriteTransactionMethod() {
    io.grpc.MethodDescriptor<zab_client.WriteTransactionRequest, com.google.protobuf.Empty> getWriteTransactionMethod;
    if ((getWriteTransactionMethod = ZabClientServiceGrpc.getWriteTransactionMethod) == null) {
      synchronized (ZabClientServiceGrpc.class) {
        if ((getWriteTransactionMethod = ZabClientServiceGrpc.getWriteTransactionMethod) == null) {
          ZabClientServiceGrpc.getWriteTransactionMethod = getWriteTransactionMethod =
              io.grpc.MethodDescriptor.<zab_client.WriteTransactionRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "WriteTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_client.WriteTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ZabClientServiceMethodDescriptorSupplier("WriteTransaction"))
              .build();
        }
      }
    }
    return getWriteTransactionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<zab_client.ReadAccountRequest,
      zab_client.ReadAccountResponse> getReadAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReadAccount",
      requestType = zab_client.ReadAccountRequest.class,
      responseType = zab_client.ReadAccountResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<zab_client.ReadAccountRequest,
      zab_client.ReadAccountResponse> getReadAccountMethod() {
    io.grpc.MethodDescriptor<zab_client.ReadAccountRequest, zab_client.ReadAccountResponse> getReadAccountMethod;
    if ((getReadAccountMethod = ZabClientServiceGrpc.getReadAccountMethod) == null) {
      synchronized (ZabClientServiceGrpc.class) {
        if ((getReadAccountMethod = ZabClientServiceGrpc.getReadAccountMethod) == null) {
          ZabClientServiceGrpc.getReadAccountMethod = getReadAccountMethod =
              io.grpc.MethodDescriptor.<zab_client.ReadAccountRequest, zab_client.ReadAccountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReadAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_client.ReadAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_client.ReadAccountResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ZabClientServiceMethodDescriptorSupplier("ReadAccount"))
              .build();
        }
      }
    }
    return getReadAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<zab_client.ReadAccountRequest,
      zab_client.ReadAccountResponse> getDebugReadAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DebugReadAccount",
      requestType = zab_client.ReadAccountRequest.class,
      responseType = zab_client.ReadAccountResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<zab_client.ReadAccountRequest,
      zab_client.ReadAccountResponse> getDebugReadAccountMethod() {
    io.grpc.MethodDescriptor<zab_client.ReadAccountRequest, zab_client.ReadAccountResponse> getDebugReadAccountMethod;
    if ((getDebugReadAccountMethod = ZabClientServiceGrpc.getDebugReadAccountMethod) == null) {
      synchronized (ZabClientServiceGrpc.class) {
        if ((getDebugReadAccountMethod = ZabClientServiceGrpc.getDebugReadAccountMethod) == null) {
          ZabClientServiceGrpc.getDebugReadAccountMethod = getDebugReadAccountMethod =
              io.grpc.MethodDescriptor.<zab_client.ReadAccountRequest, zab_client.ReadAccountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DebugReadAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_client.ReadAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_client.ReadAccountResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ZabClientServiceMethodDescriptorSupplier("DebugReadAccount"))
              .build();
        }
      }
    }
    return getDebugReadAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      zab_client.DebugHasOutstandingTransactionsResponse> getDebugHasOutstandingTransactionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DebugHasOutstandingTransactions",
      requestType = com.google.protobuf.Empty.class,
      responseType = zab_client.DebugHasOutstandingTransactionsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      zab_client.DebugHasOutstandingTransactionsResponse> getDebugHasOutstandingTransactionsMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, zab_client.DebugHasOutstandingTransactionsResponse> getDebugHasOutstandingTransactionsMethod;
    if ((getDebugHasOutstandingTransactionsMethod = ZabClientServiceGrpc.getDebugHasOutstandingTransactionsMethod) == null) {
      synchronized (ZabClientServiceGrpc.class) {
        if ((getDebugHasOutstandingTransactionsMethod = ZabClientServiceGrpc.getDebugHasOutstandingTransactionsMethod) == null) {
          ZabClientServiceGrpc.getDebugHasOutstandingTransactionsMethod = getDebugHasOutstandingTransactionsMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, zab_client.DebugHasOutstandingTransactionsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DebugHasOutstandingTransactions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  zab_client.DebugHasOutstandingTransactionsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ZabClientServiceMethodDescriptorSupplier("DebugHasOutstandingTransactions"))
              .build();
        }
      }
    }
    return getDebugHasOutstandingTransactionsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ZabClientServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZabClientServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZabClientServiceStub>() {
        @java.lang.Override
        public ZabClientServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZabClientServiceStub(channel, callOptions);
        }
      };
    return ZabClientServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ZabClientServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZabClientServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZabClientServiceBlockingStub>() {
        @java.lang.Override
        public ZabClientServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZabClientServiceBlockingStub(channel, callOptions);
        }
      };
    return ZabClientServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ZabClientServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZabClientServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZabClientServiceFutureStub>() {
        @java.lang.Override
        public ZabClientServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZabClientServiceFutureStub(channel, callOptions);
        }
      };
    return ZabClientServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void writeTransaction(zab_client.WriteTransactionRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWriteTransactionMethod(), responseObserver);
    }

    /**
     */
    default void readAccount(zab_client.ReadAccountRequest request,
        io.grpc.stub.StreamObserver<zab_client.ReadAccountResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadAccountMethod(), responseObserver);
    }

    /**
     */
    default void debugReadAccount(zab_client.ReadAccountRequest request,
        io.grpc.stub.StreamObserver<zab_client.ReadAccountResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDebugReadAccountMethod(), responseObserver);
    }

    /**
     */
    default void debugHasOutstandingTransactions(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<zab_client.DebugHasOutstandingTransactionsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDebugHasOutstandingTransactionsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ZabClientService.
   */
  public static abstract class ZabClientServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ZabClientServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ZabClientService.
   */
  public static final class ZabClientServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ZabClientServiceStub> {
    private ZabClientServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ZabClientServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZabClientServiceStub(channel, callOptions);
    }

    /**
     */
    public void writeTransaction(zab_client.WriteTransactionRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getWriteTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void readAccount(zab_client.ReadAccountRequest request,
        io.grpc.stub.StreamObserver<zab_client.ReadAccountResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void debugReadAccount(zab_client.ReadAccountRequest request,
        io.grpc.stub.StreamObserver<zab_client.ReadAccountResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDebugReadAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void debugHasOutstandingTransactions(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<zab_client.DebugHasOutstandingTransactionsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDebugHasOutstandingTransactionsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ZabClientService.
   */
  public static final class ZabClientServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ZabClientServiceBlockingStub> {
    private ZabClientServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ZabClientServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZabClientServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty writeTransaction(zab_client.WriteTransactionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getWriteTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public zab_client.ReadAccountResponse readAccount(zab_client.ReadAccountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public zab_client.ReadAccountResponse debugReadAccount(zab_client.ReadAccountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDebugReadAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public zab_client.DebugHasOutstandingTransactionsResponse debugHasOutstandingTransactions(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDebugHasOutstandingTransactionsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ZabClientService.
   */
  public static final class ZabClientServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ZabClientServiceFutureStub> {
    private ZabClientServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ZabClientServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZabClientServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> writeTransaction(
        zab_client.WriteTransactionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getWriteTransactionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<zab_client.ReadAccountResponse> readAccount(
        zab_client.ReadAccountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<zab_client.ReadAccountResponse> debugReadAccount(
        zab_client.ReadAccountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDebugReadAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<zab_client.DebugHasOutstandingTransactionsResponse> debugHasOutstandingTransactions(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDebugHasOutstandingTransactionsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_WRITE_TRANSACTION = 0;
  private static final int METHODID_READ_ACCOUNT = 1;
  private static final int METHODID_DEBUG_READ_ACCOUNT = 2;
  private static final int METHODID_DEBUG_HAS_OUTSTANDING_TRANSACTIONS = 3;

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
        case METHODID_WRITE_TRANSACTION:
          serviceImpl.writeTransaction((zab_client.WriteTransactionRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_READ_ACCOUNT:
          serviceImpl.readAccount((zab_client.ReadAccountRequest) request,
              (io.grpc.stub.StreamObserver<zab_client.ReadAccountResponse>) responseObserver);
          break;
        case METHODID_DEBUG_READ_ACCOUNT:
          serviceImpl.debugReadAccount((zab_client.ReadAccountRequest) request,
              (io.grpc.stub.StreamObserver<zab_client.ReadAccountResponse>) responseObserver);
          break;
        case METHODID_DEBUG_HAS_OUTSTANDING_TRANSACTIONS:
          serviceImpl.debugHasOutstandingTransactions((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<zab_client.DebugHasOutstandingTransactionsResponse>) responseObserver);
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
          getWriteTransactionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              zab_client.WriteTransactionRequest,
              com.google.protobuf.Empty>(
                service, METHODID_WRITE_TRANSACTION)))
        .addMethod(
          getReadAccountMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              zab_client.ReadAccountRequest,
              zab_client.ReadAccountResponse>(
                service, METHODID_READ_ACCOUNT)))
        .addMethod(
          getDebugReadAccountMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              zab_client.ReadAccountRequest,
              zab_client.ReadAccountResponse>(
                service, METHODID_DEBUG_READ_ACCOUNT)))
        .addMethod(
          getDebugHasOutstandingTransactionsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              zab_client.DebugHasOutstandingTransactionsResponse>(
                service, METHODID_DEBUG_HAS_OUTSTANDING_TRANSACTIONS)))
        .build();
  }

  private static abstract class ZabClientServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ZabClientServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return zab_client.ZabClient.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ZabClientService");
    }
  }

  private static final class ZabClientServiceFileDescriptorSupplier
      extends ZabClientServiceBaseDescriptorSupplier {
    ZabClientServiceFileDescriptorSupplier() {}
  }

  private static final class ZabClientServiceMethodDescriptorSupplier
      extends ZabClientServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ZabClientServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ZabClientServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ZabClientServiceFileDescriptorSupplier())
              .addMethod(getWriteTransactionMethod())
              .addMethod(getReadAccountMethod())
              .addMethod(getDebugReadAccountMethod())
              .addMethod(getDebugHasOutstandingTransactionsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
