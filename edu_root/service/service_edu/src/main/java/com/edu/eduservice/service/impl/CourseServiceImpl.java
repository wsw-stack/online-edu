package com.edu.eduservice.service.impl;

import com.edu.eduservice.entity.Course;
import com.edu.eduservice.entity.CourseDescription;
import com.edu.eduservice.entity.vo.CourseInfoVo;
import com.edu.eduservice.entity.vo.CoursePublishVo;
import com.edu.eduservice.mapper.CourseMapper;
import com.edu.eduservice.service.ChapterService;
import com.edu.eduservice.service.CourseDescriptionService;
import com.edu.eduservice.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.eduservice.service.VideoService;
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
    private final ChapterService chapterService;
    private final VideoService videoService;

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

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        // 查询课程和描述
        Course course = baseMapper.selectById(courseId);
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);
        BeanUtils.copyProperties(course, courseDescription);
        return courseInfoVo;
    }

    // 修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int update = baseMapper.updateById(course);
        if(update == 0) {
            throw new EduException(20001, "修改课程信息失败");
        }

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(Long id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public void removeCourse(Long courseId) {
        // 有关信息均需要删除（章节，小节，描述等）
        videoService.removeVideoByCourseId(courseId);
        chapterService.removeChapterByCourseId(courseId);
        courseDescriptionService.removeById(courseId); // 描述与课程id一一对应
        int i = baseMapper.deleteById(courseId);
        if(i == 0) {
            throw new EduException(20001, "删除失败");
        }
    }
}
