package com.moxi.minio.util;

import com.moxi.minio.entity.MinIoProperties;
import com.moxi.minio.global.SysConf;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
/**
 * 工具类
 */
@Component
public class MinIoUtils {

    @Resource
    private MinIoProperties minIo;
 

 
    private MinioClient instance;
 
    @PostConstruct
    public void init() {
        try {
            //instance = new MinioClient(minIo.getHost(), minIo.getAccessKey(), minIo.getSecretKey());
            instance= MinioClient.builder()
                    .endpoint(minIo.getHost())
                    .credentials(minIo.getAccessKey(),minIo.getSecretKey())
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
 
    /**
     * 判断 bucket是否存在
     * @param bucketName
     * @return
     */
    public boolean bucketExists(String bucketName){
        try {
            return instance.bucketExists(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 
    /**
     * 创建 bucket
     * @param bucketName
     */
    public void makeBucket(String bucketName){
        try {
            boolean isExist = instance.bucketExists(bucketName);
            if(!isExist) {
                instance.makeBucket(bucketName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 文件上传
     * @param bucketName
     * @param objectName
     * @param filename
     */
    public void putObject(String bucketName, String objectName, String filename){
        try {
            instance.putObject(bucketName,objectName,filename,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 文件上传
     * @param bucketName
     * @param objectName
     * @param stream
     */
    public String putObject(String bucketName, String objectName, InputStream stream, PutObjectOptions objectOptions){
        try {
            instance.putObject(bucketName,objectName,stream,objectOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minIo.getUrl()+bucketName+ SysConf.FILE_PARTITION+objectName;
    }
    /**
     * 删除文件
     * @param bucketName
     * @param objectName
     */
    public void removeObject(String bucketName, String objectName){
        try {
            instance.removeObject(bucketName,objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取访问路径
     */
    public String getUrl(String bucketName,String fileName) throws Exception{

        return instance.getObjectUrl(bucketName,fileName);
    }
    /**
     * 获取文件列表
     * @return
     */
    public List<String> getObjectList(String bucketName, String prefix) {
        List<String> result=new ArrayList<>();
        try {
            Iterable<Result<Item>> results = instance.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(true).build()
            );
            for (Result<Item> itemResult : results) {
                result.add(itemResult.get().objectName());
            }
        }catch (Exception e){
            e.printStackTrace();;
        }
        return result;
    }

    /**
     * 删除
     */
    public void deleteObjects(String buckName,String prefix){
        try{
            Iterable<Result<Item>> results = instance.listObjects(
                    ListObjectsArgs.builder().bucket(buckName).prefix(prefix).recursive(true).build()
            );
            List<DeleteObject> deleteObjects=new ArrayList<>();
            for (Result<Item> result : results) {
                System.out.println(result.get().objectName());
                DeleteObject object=new DeleteObject(result.get().objectName());
                deleteObjects.add(object);
            }
            System.out.println("delete done");
        }catch (Exception e){
            e.printStackTrace();;
        }
    }
}