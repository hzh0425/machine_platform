package com.moxi.minio.service;

import com.moxi.minio.entity.MultipartFileParam;
import com.moxi.minio.util.MinIoUtils;
import io.minio.PutObjectOptions;
import io.minio.errors.XmlParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.io.InputStream;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/9 20:57
 */
@Service
public class MinioService {
    @Autowired
    MinIoUtils minIoUtils;
    public String putObject(MultipartFileParam param,String buckName,String fileName){
        if(!minIoUtils.bucketExists(buckName)){
            minIoUtils.makeBucket(buckName);
        }
        String url="";
        try (InputStream inputStream=param.getFile().getInputStream()){
            MultipartFile multipartFile=param.getFile();
            PutObjectOptions putObjectOptions = new PutObjectOptions(multipartFile.getSize(), PutObjectOptions.MIN_MULTIPART_SIZE);
            // 文件的ContentType
            putObjectOptions.setContentType(multipartFile.getContentType());

            url=minIoUtils.putObject(buckName,fileName,inputStream,putObjectOptions);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public List<String> getList(String buckName, String prefix) throws XmlParserException {

        return minIoUtils.getObjectList(buckName,prefix);
    }


    public void deleteObjects(String buckName,String path)throws  Exception{
        minIoUtils.deleteObjects(buckName,path);
    }
}
