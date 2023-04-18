package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;

import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-16
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
@Autowired
    EduVideoService eduVideoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper1);

        //查小节
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id",courseId);
        List<EduVideo>  eduVideos = eduVideoService.list(wrapper2);

                List<ChapterVo> finalList=new ArrayList<>();

        for(int i=0;i<eduChapters.size();i++){
            EduChapter eduChapter = eduChapters.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            finalList.add(chapterVo);
            //创建集合，用于封装章节的小节
            List<VideoVo> videoVos = new ArrayList<>();
        for (int j = 0; j <eduVideos.size();j++ ) {

            EduVideo eduVideo = eduVideos.get(j);
            //判断：小节里面chapterid和章节里面id是否一样
            if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                //进行封装
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo,videoVo);
                //放到小节封装集合
                videoVos.add(videoVo);
        }

        }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoVos);
    }
        return finalList;
}

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
       wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        if (count > 0) {
            throw new MyException(20001,"不能删除");
        }
      else {
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
    }

    @Override
    public List getAllByCid(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);

        return eduChapters;
    }
}
