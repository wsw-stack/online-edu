package com.edu.eduservice.controller;


import com.edu.commonutils.R;
import com.edu.eduservice.entity.vo.CourseInfoVo;
import com.edu.eduservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // 添加课程信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        // 返回添加之后的课程id，用于添加大纲使用
        Long id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }
}

