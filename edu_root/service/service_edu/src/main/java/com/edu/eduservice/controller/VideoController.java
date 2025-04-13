package com.edu.eduservice.controller;


import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.commonutils.R;
import com.edu.eduservice.client.VodClient;
import com.edu.eduservice.entity.Video;
import com.edu.eduservice.service.VideoService;
import com.edu.servicebase.config.exceptionhandler.EduException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author haitong
 * @since 2025-04-02
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final VodClient vodClient;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video video) {
        videoService.save(video);
        return R.ok();
    }

    // 删除小节，并删除对应视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable Long id) {
        // 根据小节id查询视频id
        Video video = videoService.getById(id);
        if(!StringUtils.isEmpty(video.getVideoSourceId())) {
            // 如果视频id不为空
            R result = vodClient.removeAlyVideo(video.getVideoSourceId());
            if(result.getCode() == 20001) {
                throw new EduException(20001, "删除视频失败，熔断器执行");
            }
        }
        videoService.removeById(id);
        return R.ok();
    }

    // TODO 修改小节
}

