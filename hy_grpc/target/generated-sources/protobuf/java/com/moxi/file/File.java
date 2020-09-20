// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: file.proto

package com.moxi.file;

public final class File {
  private File() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_moxi_file_FileIo_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_moxi_file_FileIo_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_moxi_file_requestTemplate_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_moxi_file_requestTemplate_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_moxi_file_fileDesc_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_moxi_file_fileDesc_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_moxi_file_ReSend_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_moxi_file_ReSend_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\nfile.proto\022\rcom.moxi.file\"w\n\006FileIo\022\014\n" +
      "\004file\030\001 \001(\014\022\024\n\014currentChunk\030\002 \001(\005\022\016\n\006off" +
      "set\030\004 \001(\005\022\016\n\006length\030\005 \001(\005\022\024\n\014isChunkExis" +
      "t\030\006 \001(\010\022\023\n\013isAuthority\030\007 \001(\010\"Z\n\017requestT" +
      "emplate\022\022\n\nbucketName\030\001 \001(\t\022\016\n\006prefix\030\002 " +
      "\001(\t\022\024\n\014currentChunk\030\003 \001(\005\022\r\n\005token\030\004 \001(\t" +
      "\"V\n\010fileDesc\022\020\n\010fileName\030\001 \001(\t\022\020\n\010fileSi" +
      "ze\030\002 \001(\003\022\021\n\tchunkSize\030\003 \001(\005\022\023\n\013isAuthori" +
      "ty\030\004 \001(\010\">\n\006ReSend\022\024\n\014currentChunk\030\002 \001(\005" +
      "\022\016\n\006offset\030\004 \001(\003\022\016\n\006length\030\005 \001(\0032\344\001\n\013Fil" +
      "eService\022K\n\016getFileFromOss\022\036.com.moxi.fi" +
      "le.requestTemplate\032\025.com.moxi.file.FileI" +
      "o\"\0000\001\022H\n\013getFileDesc\022\036.com.moxi.file.req" +
      "uestTemplate\032\027.com.moxi.file.fileDesc\"\000\022" +
      ">\n\014reSendFileIo\022\025.com.moxi.file.ReSend\032\025" +
      ".com.moxi.file.FileIo\"\000B\002P\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_moxi_file_FileIo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_moxi_file_FileIo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_moxi_file_FileIo_descriptor,
        new java.lang.String[] { "File", "CurrentChunk", "Offset", "Length", "IsChunkExist", "IsAuthority", });
    internal_static_com_moxi_file_requestTemplate_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_moxi_file_requestTemplate_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_moxi_file_requestTemplate_descriptor,
        new java.lang.String[] { "BucketName", "Prefix", "CurrentChunk", "Token", });
    internal_static_com_moxi_file_fileDesc_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_com_moxi_file_fileDesc_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_moxi_file_fileDesc_descriptor,
        new java.lang.String[] { "FileName", "FileSize", "ChunkSize", "IsAuthority", });
    internal_static_com_moxi_file_ReSend_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_com_moxi_file_ReSend_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_moxi_file_ReSend_descriptor,
        new java.lang.String[] { "CurrentChunk", "Offset", "Length", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
