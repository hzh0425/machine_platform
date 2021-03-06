// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: file.proto

package com.moxi.file;

public interface FileIoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.moxi.file.FileIo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bytes file = 1;</code>
   */
  com.google.protobuf.ByteString getFile();

  /**
   * <pre>
   *当前的分块
   * </pre>
   *
   * <code>int32 currentChunk = 2;</code>
   */
  int getCurrentChunk();

  /**
   * <pre>
   *当前的偏移量
   * </pre>
   *
   * <code>int32 offset = 4;</code>
   */
  int getOffset();

  /**
   * <pre>
   *当前的长度
   * </pre>
   *
   * <code>int32 length = 5;</code>
   */
  int getLength();

  /**
   * <pre>
   *当前请求获取的分块是否存在
   * </pre>
   *
   * <code>bool isChunkExist = 6;</code>
   */
  boolean getIsChunkExist();

  /**
   * <pre>
   *当前请求者是否具有该资源的权限
   * </pre>
   *
   * <code>bool isAuthority = 7;</code>
   */
  boolean getIsAuthority();
}
