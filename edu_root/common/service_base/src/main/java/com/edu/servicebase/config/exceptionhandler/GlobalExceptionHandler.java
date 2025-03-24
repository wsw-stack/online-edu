package com.edu.servicebase.config.exceptionhandler;

import com.edu.commonutils.R;
import org.springframework.web.bind.annotation.*;

// 统一异常
@RestControllerAdvice
public class GlobalExceptionHandler {
    //指定出现什么异常会执行此方法
    @ExceptionHandler(Exception.class)
    @ResponseBody // 为了返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }
}
