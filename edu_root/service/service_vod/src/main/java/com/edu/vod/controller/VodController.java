package com.edu.vod.controller;

import com.edu.commonutils.R;
import com.edu.vod.service.VodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@RequiredArgsConstructor
@CrossOrigin
public class VodController {
    private final VodService vodService;

    // 上传视频到阿里云
    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file) {
        // 返回上传视频id
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    // 删除视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        vodService.removeVideo(id);
        return R.ok();
    }

    // 批量删除视频
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoList) {
        vodService.removeMultiAlyVideo(videoList);
        return R.ok();
    }
}
