// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: file.proto

package com.moxi.file;

public interface ReSendOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.moxi.file.ReSend)
    com.google.protobuf.MessageOrBuilder {

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
   * <code>int64 offset = 4;</code>
   */
  long getOffset();

  /**
   * <pre>
   *当前的长度
   * </pre>
   *
   * <code>int64 length = 5;</code>
   */
  long getLength();
}
