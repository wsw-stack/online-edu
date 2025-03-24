package com.edu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.commonutils.R;
import com.edu.eduservice.entity.Teacher;
import com.edu.eduservice.entity.vo.TeacherQuery;
import com.edu.eduservice.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public R findAllTeacher() {
        // null表示条件为空
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    // 2 讲师删除（逻辑删除）
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("/{id}")
    public R removeTeacher(@PathVariable String id) {
        boolean b = teacherService.removeById(id);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    // 3 分页查询
    // 代表当前页面，以及每页显示数量
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        // Page对象
        Page<Teacher> teacherPage = new Page<>(current, limit);
        // 实现分页方法
        // 参数：page对象，条件
        teacherService.page(teacherPage, null);
        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    // 4 分页条件查询
    @ApiOperation(value = "分页条件查询讲师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<Teacher> teacherPage = new Page<>(current, limit);
        // 构建条件
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!name.isEmpty()) {
            queryWrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)) {
            queryWrapper.gt("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)) {
            queryWrapper.gt("gmt_create", end);
        }
        teacherService.page(teacherPage, queryWrapper);
        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    // 5 添加讲师接口的方法
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 6 根据讲师id进行查询
    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    // 7 根据讲师id修改
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

