package com.edu.eduservice.controller;


import com.edu.commonutils.R;
import com.edu.eduservice.entity.subject.OneSubject;
import com.edu.eduservice.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author haitong
 * @since 2025-03-31
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    // 添加课程分类
    // 获取上传文件，读取文件内容
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        // 上传过来excel文件
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    // 课程分类列表
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        // 返回一级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }
}

