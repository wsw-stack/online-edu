package com.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.eduservice.client.VodClient;
import com.edu.eduservice.entity.Video;
import com.edu.eduservice.mapper.VideoMapper;
import com.edu.eduservice.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author haitong
 * @since 2025-04-02
 */
@Service
@RequiredArgsConstructor
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    private final VodClient vodClient;

    @Override
    public void removeVideoByCourseId(Long courseId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.select("video_source_id");
        List<Video> videoList = baseMapper.selectList(queryWrapper);
        List<String> videoIds = new ArrayList<>();
        for (Video video : videoList) {
            String videoSourceId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }
        // 删掉云服务中对应视频文件
        if(!videoIds.isEmpty()) {
            vodClient.deleteBatch(videoIds);
        }

        baseMapper.delete(queryWrapper);
    }
}
