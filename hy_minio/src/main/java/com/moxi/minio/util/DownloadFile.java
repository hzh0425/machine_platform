package com.moxi.minio.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/8 20:36
 *  表示要下载的哪个文件，为了能写输入到文件的指定位置，
 *  使用 RandomAccessFile 类操作文件，
 *  多个线程写同一个文件需要保证线程安全，
 *  这里直接调用 getChannel 方法，获取一个文件通道，FileChannel是线程安全的。
 */
public class DownloadFile {
    private final RandomAccessFile file;
    private final FileChannel channel; // 线程安全类
    private AtomicLong wroteSize; // 已写入的长度
    private Logger logger;

    DownloadFile(String fileName, long fileSize, Logger logger) throws IOException {
        this.wroteSize = new AtomicLong(0);
        this.logger = logger;
        this.file = new RandomAccessFile(fileName, "rw");
        file.setLength(fileSize);
        channel = file.getChannel();
    }

    /**
     * 写数据
     * @param offset 写偏移量
     * @param buffer 数据
     * @throws IOException 写数据出现异常
     */
    void write(long offset, ByteBuffer buffer, int threadID, long upperBound) throws IOException {
        //复位
        buffer.flip();
        //当前有多少的数据
        int length=buffer.limit();
        while(buffer.hasRemaining()){
            channel.write(buffer,offset);
        }
        wroteSize.addAndGet(length);
        //更新日志
        logger.updateLog(threadID,length,offset+length,upperBound);
    }

    long getWroteSize() {
        return wroteSize.get();
    }

    // 继续下载时调用
    void setWroteSize(long wroteSize) {
        this.wroteSize.set(wroteSize);
    }

    void close() {
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
