package com.edu.eduservice.service.impl;

import com.edu.eduservice.entity.Course;
import com.edu.eduservice.entity.CourseDescription;
import com.edu.eduservice.entity.vo.CourseInfoVo;
import com.edu.eduservice.mapper.CourseMapper;
import com.edu.eduservice.service.CourseDescriptionService;
import com.edu.eduservice.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.servicebase.config.exceptionhandler.EduException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author haitong
 * @since 2025-04-02
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    private final CourseDescriptionService courseDescriptionService;

    // 添加课程基本信息
    @Override
    public Long saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 1 课程表添加课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int insert = baseMapper.insert(course);
        if(insert == 0) {
            // 添加失败
            throw new EduException(20001, "添加课程信息失败");
        }
        // 添加之后课程id(使得课程与课程描述产生关联)
        Long id = course.getId();
        // 2 添加课程简介
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(id);
        courseDescriptionService.save(courseDescription);
        return id;
    }
}
