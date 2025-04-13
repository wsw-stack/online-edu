package com.edu.eduservice.service;

import com.edu.eduservice.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author haitong
 * @since 2025-04-02
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(Long courseId);
}
