package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-16
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
@Autowired
EduChapterService eduChapterService;

@GetMapping("getChapterVideo/{courseId}")
public R etChapterVideo(@PathVariable String courseId){
    List<ChapterVo> chapterVideoByCourseId = eduChapterService.getChapterVideoByCourseId(courseId);
    return R.ok().data("allChapterVideo",chapterVideoByCourseId);
}

@PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
eduChapterService.save(eduChapter);
    return R.ok();
}

@GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){

    EduChapter eduChapter = eduChapterService.getById(chapterId);
    return R.ok().data("chapter",eduChapter);
}
@PutMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {

    eduChapterService.updateById(eduChapter);
    return R.ok();
}
@DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
    boolean b = eduChapterService.deleteChapter(chapterId);
    if (b) {
        return R.ok();
    }
  else {
        return R.error();
  }
    }
}

