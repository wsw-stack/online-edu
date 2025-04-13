package com.edu.eduservice.entity.chapter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VideoVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
}
