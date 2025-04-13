package com.edu.eduservice.mapper;

import com.edu.eduservice.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.eduservice.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author haitong
 * @since 2025-04-02
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    CoursePublishVo getPublishCourseInfo(Long id);
}
