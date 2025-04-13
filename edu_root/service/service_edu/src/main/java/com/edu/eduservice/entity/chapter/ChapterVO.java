package com.edu.eduservice.entity.chapter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;

    // 表示小节
    private List<VideoVO> children = new ArrayList<>();
}
