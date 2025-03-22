package com.edu.eduservice.controller;


import com.edu.eduservice.entity.Teacher;
import com.edu.eduservice.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author haitong
 * @since 2025-03-20
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    // 1 查询讲师表所有数据
    @ApiOperation(value = "查询所有讲师")
    @GetMapping("/findAll")
    public List<Teacher> findAllTeacher() {
        // null表示条件为空
        return teacherService.list(null);
    }

    // 2 讲师删除（逻辑删除）
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("/{id}")
    public boolean removeTeacher(@PathVariable String id) {
        return teacherService.removeById(id);
    }
}

