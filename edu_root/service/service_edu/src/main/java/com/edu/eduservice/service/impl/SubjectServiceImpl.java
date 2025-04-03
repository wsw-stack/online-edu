package com.edu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.eduservice.entity.Subject;
import com.edu.eduservice.entity.excel.SubjectData;
import com.edu.eduservice.entity.subject.OneSubject;
import com.edu.eduservice.entity.subject.TwoSubject;
import com.edu.eduservice.listener.SubjectExcelListener;
import com.edu.eduservice.mapper.SubjectMapper;
import com.edu.eduservice.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author haitong
 * @since 2025-03-31
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 获取课程分类列表
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 查询一级分类
        QueryWrapper<Subject> queryWrapperOne = new QueryWrapper<>();
        queryWrapperOne.eq("parent_id", "0");
        List<Subject> subjectListOne = baseMapper.selectList(queryWrapperOne);
        // 查询二级分类
        QueryWrapper<Subject> queryWrapperTwo = new QueryWrapper<>();
        queryWrapperTwo.ne("parent_id", "0");
        List<Subject> subjectListTwo = baseMapper.selectList(queryWrapperTwo);
        // 集合存储最终封装数据
        List<OneSubject> subjectList = new ArrayList<>();
        // 封装一级分类到集合
        for (int i = 0; i < subjectListOne.size(); i++) {
            Subject subject = subjectListOne.get(i);
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(subject.getId());
            oneSubject.setTitle(subject.getTitle());

            subjectList.add(oneSubject);
            // 遍历查询二级分类
            List<TwoSubject> twoSubjectList = new ArrayList<>();
            for (Subject subject1 : subjectListTwo) {
                if(Objects.equals(subject1.getParentId(), oneSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subject1, twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }
            // 封装二级分类
            oneSubject.setChildren(twoSubjectList);
        }
        return subjectList;
    }
}
