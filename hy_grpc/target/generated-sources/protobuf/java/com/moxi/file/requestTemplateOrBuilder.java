// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: file.proto

package com.moxi.file;

public interface requestTemplateOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.moxi.file.requestTemplate)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *桶的名字(用户uid)
   * </pre>
   *
   * <code>string bucketName = 1;</code>
   */
  java.lang.String getBucketName();
  /**
   * <pre>
   *桶的名字(用户uid)
   * </pre>
   *
   * <code>string bucketName = 1;</code>
   */
  com.google.protobuf.ByteString
      getBucketNameBytes();

  /**
   * <pre>
   *文件前缀(文件的uid)
   * </pre>
   *
   * <code>string prefix = 2;</code>
   */
  java.lang.String getPrefix();
  /**
   * <pre>
   *文件前缀(文件的uid)
   * </pre>
   *
   * <code>string prefix = 2;</code>
   */
  com.google.protobuf.ByteString
      getPrefixBytes();

  /**
   * <pre>
   *要获取哪一块(从1开始)
   * </pre>
   *
   * <code>int32 currentChunk = 3;</code>
   */
  int getCurrentChunk();

  /**
   * <pre>
   *资源令牌,后面用于做权限认证
   * </pre>
   *
   * <code>string token = 4;</code>
   */
  java.lang.String getToken();
  /**
   * <pre>
   *资源令牌,后面用于做权限认证
   * </pre>
   *
   * <code>string token = 4;</code>
   */
  com.google.protobuf.ByteString
      getTokenBytes();
}
