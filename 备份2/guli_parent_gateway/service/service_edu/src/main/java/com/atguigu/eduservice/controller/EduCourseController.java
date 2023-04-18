package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/eduservice/course")
public class EduCourseController {
@Autowired
    EduVideoService eduVideoService;
    @Autowired
    EduCourseService eduCourseService;
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加之后课程id，为了后面添加大纲使用
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }
    @GetMapping("getCourseInfo/{courseId}")
    public  R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo=  eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //update
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
eduCourseService.updateCourseInfo(courseInfoVo);
return R.ok();

    }
    //publish1
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }
    //publish2
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = eduCourseService.getById(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //getListCourse
    @GetMapping("getListCourse")
    public R getListCourse(){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        List<EduCourse> list = eduCourseService.list(wrapper);
        return R.ok().data("list",list);

    }
    //deleteCourse diy

/*
    @DeleteMapping("{id}")
    public R deleteCourseDiy(@PathVariable String id) {
        eduCourseService.removeById(id);
        if(eduCourseService.removeById(id)) return R.ok();
        else {
            return R.error();
        }

    }
*/

    //deleteCourse
    @DeleteMapping("{deleteid}")
    public R deleteCourse(@PathVariable String deleteid) {

        //删除章节小节描述信息
        eduCourseService.removeCourse(deleteid);

        //删除课程
        eduCourseService.removeById(deleteid);


        return R.ok();
    }
    //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = eduCourseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }

}

