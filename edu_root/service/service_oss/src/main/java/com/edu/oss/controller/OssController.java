package com.edu.oss.controller;

import com.edu.commonutils.R;
import com.edu.oss.service.OssService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
@RequiredArgsConstructor
public class OssController {
    private final OssService ossService;

    // 上传头像
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        // 获取上传文件
        // 方法返回上传到OSS路径
        String url = ossService.uploadAvatar(file);
        return R.ok().data("url", url);
    }
}
