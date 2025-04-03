package com.edu.eduservice.service;

import com.edu.eduservice.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author haitong
 * @since 2025-03-31
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file, SubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
