package com.edu.eduservice.service;

import com.edu.eduservice.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author haitong
 * @since 2025-04-02
 */
public interface CourseService extends IService<Course> {

    Long saveCourseInfo(CourseInfoVo courseInfoVo);
}
