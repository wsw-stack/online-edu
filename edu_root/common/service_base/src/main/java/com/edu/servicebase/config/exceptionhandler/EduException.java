package com.edu.servicebase.config.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduException extends RuntimeException{
    private Integer code;
    private String msg;
}
