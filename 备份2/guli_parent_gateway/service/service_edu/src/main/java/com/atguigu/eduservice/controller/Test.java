package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Test {
    @Autowired
    EduTeacherService eduTeacherService;
    @GetMapping("find")
    public List<EduTeacher> findAll(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return list;
    }
    @GetMapping("find2")
    public R findAll2(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("data",list);
    }
    @ApiOperation(value = "delete!!!")
    @DeleteMapping("delete/{id}")
    public  boolean delete(@PathVariable String id){

        return  eduTeacherService.removeById(id);
    }

    @GetMapping("page/{current}/{limit}")
    public R pageTest(@PathVariable long current,@PathVariable long limit) {
        Page<EduTeacher> page1 = new Page<>(current, limit);
        eduTeacherService.page(page1,null);

        long total = page1.getTotal();
        List<EduTeacher> records = page1.getRecords();
        return R.ok().data("total",total).data("rows",records);


    }
// condition select


    @PostMapping("condition/{current}/{limit}")
    public R condition(@PathVariable long current,@PathVariable long limit,@RequestBody(required = false)
            TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!org.springframework.util.StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }
        if(!org.springframework.util.StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!org.springframework.util.StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }
       eduTeacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }


    //修改
    @PostMapping("update")
    public R changeTest(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        }
        try{
            int i=10/0;
        } catch (Exception e) {
           throw new MyException(500,"myerror");
        }
        return R.error();
    }
}
