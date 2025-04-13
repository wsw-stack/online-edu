package com.edu.eduservice.client;

import com.edu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

// 出错后执行
@Component
public class VodFailDegradeFeignClient implements VodClient{
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("批量删除视频出错");
    }
}
