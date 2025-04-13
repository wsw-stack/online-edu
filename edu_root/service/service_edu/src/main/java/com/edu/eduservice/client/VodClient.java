package com.edu.eduservice.client;

import com.edu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod", fallback = VodFailDegradeFeignClient.class) // 整合熔断器
public interface VodClient {
    // 定义调用的方法路径
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    R removeAlyVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/delete-batch")
    R deleteBatch(@RequestParam("videoIdList") List<String> videoList);
}
