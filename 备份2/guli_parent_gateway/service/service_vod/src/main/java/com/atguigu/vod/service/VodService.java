package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    void removeByIds(List<String> videoIdList);


    //上传视频到阿里云
    String uploadVideoAly(MultipartFile file);

    void removeById(String id);
}
