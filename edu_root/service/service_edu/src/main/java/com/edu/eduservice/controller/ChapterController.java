package com.edu.eduservice.controller;


import com.edu.commonutils.R;
import com.edu.eduservice.entity.Chapter;
import com.edu.eduservice.entity.chapter.ChapterVO;
import com.edu.eduservice.service.ChapterService;
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
@RequestMapping("/edu/chapter")
@CrossOrigin
@RequiredArgsConstructor
public class ChapterController {
    private final ChapterService chapterService;

    // 根据课程id查询课程大纲列表
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVO> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    // 添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    // 根据id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        Chapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    // 修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }

    // 删除
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable Long chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag) {
            return R.ok();
        }
        return R.error();
    }
}

