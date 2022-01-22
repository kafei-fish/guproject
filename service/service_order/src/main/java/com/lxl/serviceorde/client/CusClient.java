package com.lxl.serviceorde.client;

import com.lxl.commonutils.vo.CourseWebVoInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-edu")
@Component
public interface CusClient {
    @GetMapping("/serviceedu/courseFrom/getCourseByCourseId/{courseId}")
    public CourseWebVoInfo getCourseByCourseId(@PathVariable("courseId") String courseId);
}
