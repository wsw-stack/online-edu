package com.edu.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.edu.oss.service.OssService;
import com.edu.oss.utils.PropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    // 上传文件到OSS
    @Override
    public String uploadAvatar(MultipartFile file) {
        String endpoint = PropertiesUtils.ENDPOINT;
        String accessKeyId = PropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = PropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = PropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            // 生成随机的唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            // 文件按照日期分类
            String date = new DateTime().toString("yyyy/MM/dd");
            fileName = date + "/" + uuid + fileName;
            //文件上传至阿里云
            ossClient.putObject(bucketName, fileName, inputStream);
            ossClient.shutdown();
            // 返回上传后的路径
            return "https://" + bucketName + "." + endpoint + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
