package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.MyException;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-16
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    private VodClient vodClient;
    @Autowired
    EduVideoService eduVideoService;

    @PostMapping("addVideo")
    public R addVideot(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }
    //删除小节
    // TODO 后面这个方法需要完善：删除小节时候，同时把里面视频删除

    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        EduVideo eduVideo = eduVideoService.getById(id);

        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmptyOrWhitespaceOnly(videoSourceId)){
            //删除阿里云
            R r = vodClient.removeAlyVideo(videoSourceId);
            //删除数据库
            eduVideoService.removeById(id);
          /*  if(r.getCode()==20001){
                throw new MyException(20001,"删除异常");
            }*/
        }
        else{
            eduVideoService.removeById(id);
        }
        return R.ok();
    }

    //修改小节 TODO

}

