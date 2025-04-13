package com.edu.eduservice.controller;


import com.edu.commonutils.R;
import com.edu.eduservice.entity.Course;
import com.edu.eduservice.entity.vo.CourseInfoVo;
import com.edu.eduservice.entity.vo.CoursePublishVo;
import com.edu.eduservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author haitong
 * @since 2025-04-02
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    // 课程列表
    @GetMapping
    public R getCourseList() {
        List<Course> list = courseService.list(null);
        return R.ok().data("list", list);
    }

    // 添加课程信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        // 返回添加之后的课程id，用于添加大纲使用
        Long id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id.toString());
    }

    // 根据课程查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    // 修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    // 根据id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable Long id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    // 课程最终发布
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable Long id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    // 删除课程（同时删除对应小节）
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable Long courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

