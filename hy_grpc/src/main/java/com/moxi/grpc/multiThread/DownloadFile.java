package com.moxi.grpc.multiThread;

import java.io.BufferedInputStream;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/12 23:34
 *
 *  *  表示要读取的总的bufferInputStream的指定位置
 */
public class DownloadFile {
    public BufferedInputStream inputStream;
    private AtomicLong readSize;//已经读取的长度
    DownloadFile(BufferedInputStream inputStream){
        this.inputStream=inputStream;
        this.readSize=new AtomicLong(0);
    }
}
