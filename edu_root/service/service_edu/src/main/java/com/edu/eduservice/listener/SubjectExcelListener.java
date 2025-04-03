package com.edu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.eduservice.entity.Subject;
import com.edu.eduservice.entity.excel.SubjectData;
import com.edu.eduservice.service.SubjectService;
import com.edu.servicebase.config.exceptionhandler.EduException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    public SubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // 一行一行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null) { // excel没有数据
            throw new EduException(20001, "文件数据为空");
        }
        // 判断一级分类是否重复，无重复则进行添加
        Subject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(existOneSubject == null) { // 没有相同一级分类，进行添加
            existOneSubject = new Subject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }

        // 判断二级分类是否重复，无重复则进行添加
        Subject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getOneSubjectName(), existOneSubject.getId());
        if(existTwoSubject == null) { // 没有相同一级分类，进行添加
            existTwoSubject = new Subject();
            existTwoSubject.setParentId(existOneSubject.getId());
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }
    }

    // 判断一级分类不能重复添加
    private Subject existOneSubject(SubjectService subjectService, String name) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        return subjectService.getOne(wrapper);
    }
    // 判断二级分类不能重复添加
    private Subject existTwoSubject(SubjectService subjectService, String name, String parentId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", parentId);
        return subjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
