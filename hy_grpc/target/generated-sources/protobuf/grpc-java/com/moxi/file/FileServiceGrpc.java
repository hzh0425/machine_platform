package com.moxi.file;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 *重载函数
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.16.1)",
    comments = "Source: file.proto")
public final class FileServiceGrpc {

  private FileServiceGrpc() {}

  public static final String SERVICE_NAME = "com.moxi.file.FileService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.moxi.file.requestTemplate,
      com.moxi.file.FileIo> getGetFileFromOssMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getFileFromOss",
      requestType = com.moxi.file.requestTemplate.class,
      responseType = com.moxi.file.FileIo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.moxi.file.requestTemplate,
      com.moxi.file.FileIo> getGetFileFromOssMethod() {
    io.grpc.MethodDescriptor<com.moxi.file.requestTemplate, com.moxi.file.FileIo> getGetFileFromOssMethod;
    if ((getGetFileFromOssMethod = FileServiceGrpc.getGetFileFromOssMethod) == null) {
      synchronized (FileServiceGrpc.class) {
        if ((getGetFileFromOssMethod = FileServiceGrpc.getGetFileFromOssMethod) == null) {
          FileServiceGrpc.getGetFileFromOssMethod = getGetFileFromOssMethod = 
              io.grpc.MethodDescriptor.<com.moxi.file.requestTemplate, com.moxi.file.FileIo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "com.moxi.file.FileService", "getFileFromOss"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.moxi.file.requestTemplate.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.moxi.file.FileIo.getDefaultInstance()))
                  .setSchemaDescriptor(new FileServiceMethodDescriptorSupplier("getFileFromOss"))
                  .build();
          }
        }
     }
     return getGetFileFromOssMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.moxi.file.requestTemplate,
      com.moxi.file.fileDesc> getGetFileDescMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getFileDesc",
      requestType = com.moxi.file.requestTemplate.class,
      responseType = com.moxi.file.fileDesc.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.moxi.file.requestTemplate,
      com.moxi.file.fileDesc> getGetFileDescMethod() {
    io.grpc.MethodDescriptor<com.moxi.file.requestTemplate, com.moxi.file.fileDesc> getGetFileDescMethod;
    if ((getGetFileDescMethod = FileServiceGrpc.getGetFileDescMethod) == null) {
      synchronized (FileServiceGrpc.class) {
        if ((getGetFileDescMethod = FileServiceGrpc.getGetFileDescMethod) == null) {
          FileServiceGrpc.getGetFileDescMethod = getGetFileDescMethod = 
              io.grpc.MethodDescriptor.<com.moxi.file.requestTemplate, com.moxi.file.fileDesc>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.moxi.file.FileService", "getFileDesc"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.moxi.file.requestTemplate.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.moxi.file.fileDesc.getDefaultInstance()))
                  .setSchemaDescriptor(new FileServiceMethodDescriptorSupplier("getFileDesc"))
                  .build();
          }
        }
     }
     return getGetFileDescMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.moxi.file.ReSend,
      com.moxi.file.FileIo> getReSendFileIoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reSendFileIo",
      requestType = com.moxi.file.ReSend.class,
      responseType = com.moxi.file.FileIo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.moxi.file.ReSend,
      com.moxi.file.FileIo> getReSendFileIoMethod() {
    io.grpc.MethodDescriptor<com.moxi.file.ReSend, com.moxi.file.FileIo> getReSendFileIoMethod;
    if ((getReSendFileIoMethod = FileServiceGrpc.getReSendFileIoMethod) == null) {
      synchronized (FileServiceGrpc.class) {
        if ((getReSendFileIoMethod = FileServiceGrpc.getReSendFileIoMethod) == null) {
          FileServiceGrpc.getReSendFileIoMethod = getReSendFileIoMethod = 
              io.grpc.MethodDescriptor.<com.moxi.file.ReSend, com.moxi.file.FileIo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.moxi.file.FileService", "reSendFileIo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.moxi.file.ReSend.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.moxi.file.FileIo.getDefaultInstance()))
                  .setSchemaDescriptor(new FileServiceMethodDescriptorSupplier("reSendFileIo"))
                  .build();
          }
        }
     }
     return getReSendFileIoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FileServiceStub newStub(io.grpc.Channel channel) {
    return new FileServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FileServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new FileServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FileServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new FileServiceFutureStub(channel);
  }

  /**
   * <pre>
   *重载函数
   * </pre>
   */
  public static abstract class FileServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1.获取目标文件的某一块,结果会告知总共多少块,当前请求的块是否存在
     * </pre>
     */
    public void getFileFromOss(com.moxi.file.requestTemplate request,
        io.grpc.stub.StreamObserver<com.moxi.file.FileIo> responseObserver) {
      asyncUnimplementedUnaryCall(getGetFileFromOssMethod(), responseObserver);
    }

    /**
     * <pre>
     *2.获取目标文件的描述
     * </pre>
     */
    public void getFileDesc(com.moxi.file.requestTemplate request,
        io.grpc.stub.StreamObserver<com.moxi.file.fileDesc> responseObserver) {
      asyncUnimplementedUnaryCall(getGetFileDescMethod(), responseObserver);
    }

    /**
     * <pre>
     *3.client通知service重传某一块io(做完后再补充该功能)
     * </pre>
     */
    public void reSendFileIo(com.moxi.file.ReSend request,
        io.grpc.stub.StreamObserver<com.moxi.file.FileIo> responseObserver) {
      asyncUnimplementedUnaryCall(getReSendFileIoMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetFileFromOssMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                com.moxi.file.requestTemplate,
                com.moxi.file.FileIo>(
                  this, METHODID_GET_FILE_FROM_OSS)))
          .addMethod(
            getGetFileDescMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.moxi.file.requestTemplate,
                com.moxi.file.fileDesc>(
                  this, METHODID_GET_FILE_DESC)))
          .addMethod(
            getReSendFileIoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.moxi.file.ReSend,
                com.moxi.file.FileIo>(
                  this, METHODID_RE_SEND_FILE_IO)))
          .build();
    }
  }

  /**
   * <pre>
   *重载函数
   * </pre>
   */
  public static final class FileServiceStub extends io.grpc.stub.AbstractStub<FileServiceStub> {
    private FileServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1.获取目标文件的某一块,结果会告知总共多少块,当前请求的块是否存在
     * </pre>
     */
    public void getFileFromOss(com.moxi.file.requestTemplate request,
        io.grpc.stub.StreamObserver<com.moxi.file.FileIo> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetFileFromOssMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2.获取目标文件的描述
     * </pre>
     */
    public void getFileDesc(com.moxi.file.requestTemplate request,
        io.grpc.stub.StreamObserver<com.moxi.file.fileDesc> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetFileDescMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3.client通知service重传某一块io(做完后再补充该功能)
     * </pre>
     */
    public void reSendFileIo(com.moxi.file.ReSend request,
        io.grpc.stub.StreamObserver<com.moxi.file.FileIo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReSendFileIoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   *重载函数
   * </pre>
   */
  public static final class FileServiceBlockingStub extends io.grpc.stub.AbstractStub<FileServiceBlockingStub> {
    private FileServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1.获取目标文件的某一块,结果会告知总共多少块,当前请求的块是否存在
     * </pre>
     */
    public java.util.Iterator<com.moxi.file.FileIo> getFileFromOss(
        com.moxi.file.requestTemplate request) {
      return blockingServerStreamingCall(
          getChannel(), getGetFileFromOssMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *2.获取目标文件的描述
     * </pre>
     */
    public com.moxi.file.fileDesc getFileDesc(com.moxi.file.requestTemplate request) {
      return blockingUnaryCall(
          getChannel(), getGetFileDescMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *3.client通知service重传某一块io(做完后再补充该功能)
     * </pre>
     */
    public com.moxi.file.FileIo reSendFileIo(com.moxi.file.ReSend request) {
      return blockingUnaryCall(
          getChannel(), getReSendFileIoMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *重载函数
   * </pre>
   */
  public static final class FileServiceFutureStub extends io.grpc.stub.AbstractStub<FileServiceFutureStub> {
    private FileServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *2.获取目标文件的描述
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.moxi.file.fileDesc> getFileDesc(
        com.moxi.file.requestTemplate request) {
      return futureUnaryCall(
          getChannel().newCall(getGetFileDescMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     *3.client通知service重传某一块io(做完后再补充该功能)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.moxi.file.FileIo> reSendFileIo(
        com.moxi.file.ReSend request) {
      return futureUnaryCall(
          getChannel().newCall(getReSendFileIoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_FILE_FROM_OSS = 0;
  private static final int METHODID_GET_FILE_DESC = 1;
  private static final int METHODID_RE_SEND_FILE_IO = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FileServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FileServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_FILE_FROM_OSS:
          serviceImpl.getFileFromOss((com.moxi.file.requestTemplate) request,
              (io.grpc.stub.StreamObserver<com.moxi.file.FileIo>) responseObserver);
          break;
        case METHODID_GET_FILE_DESC:
          serviceImpl.getFileDesc((com.moxi.file.requestTemplate) request,
              (io.grpc.stub.StreamObserver<com.moxi.file.fileDesc>) responseObserver);
          break;
        case METHODID_RE_SEND_FILE_IO:
          serviceImpl.reSendFileIo((com.moxi.file.ReSend) request,
              (io.grpc.stub.StreamObserver<com.moxi.file.FileIo>) responseObserver);
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

  private static abstract class FileServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FileServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.moxi.file.File.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FileService");
    }
  }

  private static final class FileServiceFileDescriptorSupplier
      extends FileServiceBaseDescriptorSupplier {
    FileServiceFileDescriptorSupplier() {}
  }

  private static final class FileServiceMethodDescriptorSupplier
      extends FileServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FileServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (FileServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FileServiceFileDescriptorSupplier())
              .addMethod(getGetFileFromOssMethod())
              .addMethod(getGetFileDescMethod())
              .addMethod(getReSendFileIoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
