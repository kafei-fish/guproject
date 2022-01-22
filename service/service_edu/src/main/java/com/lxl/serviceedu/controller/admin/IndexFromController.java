package com.lxl.serviceedu.controller.admin;


import com.lxl.commonutils.R;
import com.lxl.serviceedu.entity.EduCourse;
import com.lxl.serviceedu.entity.EduTeacher;
import com.lxl.serviceedu.service.EduCourseService;
import com.lxl.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/serviceedu/indexCourseAndTeacher")

public class IndexFromController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;
    @GetMapping("index")
    public R index(){
        //首先获取课程前8个
        List<EduCourse> courseList=courseService.getCourseList();
        //获取讲师前4名
        List<EduTeacher> teacherList=teacherService.getTeacherList();
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
