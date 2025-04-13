package com.edu.eduservice.service;

import com.edu.eduservice.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.eduservice.entity.chapter.ChapterVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author haitong
 * @since 2025-04-02
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVO> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(Long id);

    void removeChapterByCourseId(Long courseId);
}
