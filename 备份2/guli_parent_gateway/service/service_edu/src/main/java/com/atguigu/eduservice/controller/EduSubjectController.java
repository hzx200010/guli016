package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-14
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;
    @PostMapping("addSubject")
public R addSubject(MultipartFile file) throws IOException {
    eduSubjectService.saveSubject(file,eduSubjectService);
    return R.ok();
}
@GetMapping("getAllSubject")
    public R getAllSubject() {
    List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
    return R.ok().data("list",list);
    }

}

