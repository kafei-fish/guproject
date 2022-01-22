package com.lxl.serviceedu.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxl.commonutils.JwtUtils;
import com.lxl.commonutils.R;
import com.lxl.commonutils.vo.CourseWebVoInfo;
import com.lxl.serviceedu.client.OrderClient;
import com.lxl.serviceedu.entity.EduCourse;
import com.lxl.serviceedu.entity.chapter.ChapterVo;
import com.lxl.serviceedu.entity.vo.CourseQueryVo;
import com.lxl.serviceedu.entity.vo.CourseWebVo;
import com.lxl.serviceedu.service.EduChapterService;
import com.lxl.serviceedu.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/serviceedu/courseFrom")

public class CourseFromController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private EduChapterService chapterService;
    @GetMapping("{page}/{limit}")
    public R pageList(@PathVariable Long page,@PathVariable Long limit){
        Page<EduCourse> pram=new Page<>(page,limit);

        Map<String,Object> map=courseService.pageList(pram);
        return R.ok().data("map",map);
    }
    @PostMapping("{page}/{limit}")
    public R pageQueryList(@PathVariable Long page, @PathVariable Long limit ,
                           @RequestBody CourseQueryVo courseVo){
        Page<EduCourse> pram=new Page<>(page,limit);

        Map<String,Object> map=courseService.pageQueyList(pram,courseVo);
        return R.ok().data("map",map);
    }
    @GetMapping("course/{courseId}")
    public R getCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        boolean buyCourse = orderClient.buyCourse( JwtUtils.getMemberIdByJwtToken(request),courseId);

        //通过课程Id来查询初课程
        CourseWebVo courseWebVo=courseService.getCourseId(courseId);
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoById(courseId);

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVoList",chapterVoList).data("isbuy",buyCourse);
    }
    @GetMapping("getCourseByCourseId/{courseId}")
    public CourseWebVoInfo getCourseByCourseId(@PathVariable String courseId){
        CourseWebVo courseInfoVo = courseService.getCourseId(courseId);
        CourseWebVoInfo eduCourseVo=new CourseWebVoInfo();
        BeanUtils.copyProperties(courseInfoVo,eduCourseVo);
        return eduCourseVo;
    }
}
