package com.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.commonutils.R;
import com.edu.eduservice.entity.Chapter;
import com.edu.eduservice.entity.Video;
import com.edu.eduservice.entity.chapter.ChapterVO;
import com.edu.eduservice.entity.chapter.VideoVO;
import com.edu.eduservice.mapper.ChapterMapper;
import com.edu.eduservice.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.eduservice.service.VideoService;
import com.edu.servicebase.config.exceptionhandler.EduException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author haitong
 * @since 2025-04-02
 */
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    private final VideoService videoService;

    // 根据课程id查询课程大纲列表
    @Override
    public List<ChapterVO> getChapterVideoByCourseId(String courseId) {
        // 课程id查询课程里面章节
        QueryWrapper<Chapter> courseWrapper = new QueryWrapper<>();
        courseWrapper.eq("course_id", courseId);
        List<Chapter> chapterList = baseMapper.selectList(courseWrapper);
        // 根据课程id查询小节
        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<Video> videoList = videoService.list(videoWrapper);
        // 创建集合用于最终封装VO数据
        List<ChapterVO> finalList = new ArrayList<>();
        // 遍历查询章节list集合进行封装
        for (Chapter chapter : chapterList) {
            // 复制对象的值
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(chapter, chapterVO);
            finalList.add(chapterVO);

            // 遍历小节
            List<VideoVO> videoVOList = new ArrayList<>();
            for (Video video : videoList) {
                // 判断video是否是当前章节的小节
                if(video.getChapterId().equals(chapter.getId())) {
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(video, videoVO);
                    videoVOList.add(videoVO);
                }
            }
            chapterVO.setChildren(videoVOList);
        }
        // 查询list小节集合
        return finalList;
    }

    @Override
    public boolean deleteChapter(Long id) {
        // 判断章节下面是否有小节，如有，不允许删除
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", id);
        // 查看有几条记录
        int count = videoService.count(queryWrapper);
        // 有小节，不进行删除
        if(count > 0) {
            throw new EduException(20001, "当前章节下面仍有小节，不能删除");
        } else {
            int result = baseMapper.deleteById(id);
            return result > 0;
        }
    }

    @Override
    public void removeChapterByCourseId(Long courseId) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        baseMapper.delete(queryWrapper);
    }
}
