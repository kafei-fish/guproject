package com.lxl.serviceedu.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxl.commonutils.R;
import com.lxl.serviceedu.entity.EduTeacher;
import com.lxl.serviceedu.service.EduTeacherService;
import com.lxl.serviceedu.entity.EduCourse;
import com.lxl.serviceedu.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping("/serviceedu/teacherFrom")
@RestController

public class TeacherFromController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;
    /**
     * 前端讲师分页
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageList/{page}/{limit}")
    public R getPageList(@PathVariable Long page, @PathVariable Long limit){
        Page<EduTeacher> pram=new Page<>(page,limit);
        Map<String,Object> map=teacherService.pageListWeb(pram);
        return R.ok().data(map);
    }
        @GetMapping("getTeacherById/{id}")
    public R getTeacherById(@PathVariable String id){
        EduTeacher eduTeacher=teacherService.getTeacherById(id);
        List<EduCourse> courseList =courseService.selectByTeacherId(id);
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }
}
