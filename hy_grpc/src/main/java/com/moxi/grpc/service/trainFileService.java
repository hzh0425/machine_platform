package com.moxi.grpc.service;

import com.google.protobuf.ByteString;
import com.moxi.file.FileIo;
import com.moxi.file.FileServiceGrpc;
import com.moxi.file.fileDesc;
import com.moxi.file.requestTemplate;
import com.moxi.grpc.bean.minioFile;
import com.moxi.grpc.global.SysConf;
import com.moxi.grpc.multiThread.DownloadTask;
import com.moxi.grpc.util.MinIoUtils;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/12 20:17
 */
@GRpcService
@Service
public class trainFileService extends FileServiceGrpc.FileServiceImplBase{
    @Autowired
    MinIoUtils minIoUtils;
    @Value("${upload.bufferSize}")
    public  int bufferSize;
    @Value("${upload.blockSize}")
    public  long blockSize;//每个线程下载的大小
    @Value("${upload.readSize}")
    public  long readSize;

    /**
     * 以下请求的request template
     */
    /**
     * 获取目标文件的信息
     * @param request
     * @param responseObserver
     *
     * message  fileDesc{
     *   string fileName=1; //名称
     *   int64 fileSize=2; //大小
     *   int32 chunkSize=3;//块数,选填
     *   bool  isAuthority=4;//当前请求者是否具有该资源的权限
     * }
     */
    @Override
    public void getFileDesc(requestTemplate request, StreamObserver<fileDesc> responseObserver) {
        String buckName=request.getBucketName();
        String prefix=request.getPrefix();
        List<minioFile> list=minIoUtils.getObjectList(buckName,prefix);
        int chunks=list.size();
        /**
         * 1.待补充权限认证,文件信息描述
         */

        /**
         * 2.构造文件信息描述
         */
        Optional<Long> sumSize=list.stream().map(x->x.getSize()).reduce((x1, x2)->x1+x2);
        fileDesc desc= fileDesc.newBuilder()
                .setChunkSize(chunks)
                .setFileSize(sumSize.get())
                .setFileName("test")
                .setIsAuthority(true).build();
        responseObserver.onNext(desc);
        responseObserver.onCompleted();
    }

    /**
     * 获取目标文件的某一块
     * @param request
     * @param responseObserver
     */
    @Override
    public void getFileFromOss(requestTemplate request, StreamObserver<FileIo> responseObserver) {

        //1.获取minio urlLists
        String buckName=request.getBucketName();
        String prefix=request.getPrefix();
        int curChunk=request.getCurrentChunk();
        List<minioFile> fileList=minIoUtils.getObjectList(buckName,prefix);

        //2.判断当前请求的块是否存在
        boolean isExist=fileList.size()>=curChunk;
        if(!isExist){
           //如果超出了块的数量
           FileIo fileIo= FileIo.newBuilder()
                   .setIsChunkExist(false).build();
           responseObserver.onNext(fileIo);
           responseObserver.onCompleted();
           return;
        }else{
            minioFile curFile=fileList.get(curChunk-1);
            byte[]buffer=new byte[bufferSize];
            int byteRead=0;
            int sum=0;
            try (
                    InputStream inputStream=minIoUtils.getObjectAsIo(buckName,curFile.getPath());
                    //转化为Buffer缓冲区类型
                    BufferedInputStream stream=new BufferedInputStream(inputStream);
            ){
                while((byteRead=stream.read(buffer))!=-1){
                    System.out.println("发送数据大小:"+byteRead);
                    FileIo file= FileIo.newBuilder()
                            //当前块
                            .setCurrentChunk(curFile.getCurrentChunk())
                            //当前位置
                            .setOffset(sum)
                            //偏移量
                            .setLength(byteRead)
                            //byte文件
                            .setFile(ByteString.copyFrom(buffer)).build();
                    responseObserver.onNext(file);
                    sum+=byteRead+1;
                }
                responseObserver.onCompleted();
            }catch (Exception e){
                e.printStackTrace();
            }
//            minioFile file=fileList.get(curChunk-1);
//            String objectName=file.getPath();
//            //分配线程
//            try (
//                    InputStream inputStream=minIoUtils.getObjectAsIo(buckName,objectName);
//                    //转化为Buffer缓冲区类型
//                    BufferedInputStream stream=new BufferedInputStream(inputStream);
//                ){
//
//                System.out.println("开始分配线程");
//                dispatch(file.getSize(),blockSize,buckName,objectName,responseObserver,stream);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            responseObserver.onCompleted();
        }
    }


    //分配子线程
    public void dispatch(long fileSize,long blockSize,String buckName,String objectName,StreamObserver<FileIo> responseObserver,BufferedInputStream stream) throws Exception {
        int theadCount= (int) (fileSize/blockSize);
        long lower=0;
        long upper=0;
        int threadId=0;
        System.out.println(theadCount);
        for (int i=0;i<theadCount;i++){
            threadId=i+1;
            lower=i*blockSize;
            upper=(i==theadCount-1?fileSize-1:lower+blockSize);
            //new DownloadTask(lower,upper,responseObserver,stream,threadId,readSize).start();
        }

    }
}
