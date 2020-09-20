package com.moxi.grpc.multiThread;

import com.google.protobuf.ByteString;
import com.moxi.file.FileIo;
import com.moxi.grpc.util.MinIoUtils;
import io.grpc.stub.StreamObserver;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.ConstructorProperties;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 作用:子线程,用于传输从LowerBound到UpperBound的数据
 *
 * @author hzh
 * @version 1.0
 * @date 2020/9/12 23:43
 */
@Data
public class DownloadTask extends Thread{

    private String buckName;    //Minio桶名
    private String objectName;  //文件名
    private StreamObserver<FileIo> responseObserver;//grpc response
    private long lowerBound;//下载下限
    private long upperBound;//下载上限
    private long readSize;
    private long bufferSize;
    private int ThreadId;//当前线程id
    private BufferedInputStream stream;

    public DownloadTask(long lower, long upperBound, StreamObserver<FileIo> observer,BufferedInputStream stream, int id,long readSize,long bufferSize) throws Exception {
        this.lowerBound=lower;
        this.upperBound=upperBound;
        this.responseObserver=observer;
        this.ThreadId=id;
        this.stream=stream;
        this.readSize=readSize;
        this.bufferSize=bufferSize;
        System.out.println("线程 "+id+" 初始化完毕");
    }


    @SneakyThrows
    @Override
    public void run() {
        byte[] bytes=new byte[(int)bufferSize];
        int byteRead=0;
        try {
            while(lowerBound<=upperBound){
                byteRead=stream.read(bytes);
                FileIo fileIo= FileIo.newBuilder()
                        .setFile(ByteString.copyFrom(bytes))
                        .setOffset((int) lowerBound)
                        .setLength(byteRead)
                        .build();
                lowerBound+=byteRead;
                //异步返回
                this.responseObserver.onNext(fileIo);
            }
        }catch (Exception e){
            e.printStackTrace();;
        }finally {
            if(stream!=null){
                stream.close();
            }
        }
    }


}
