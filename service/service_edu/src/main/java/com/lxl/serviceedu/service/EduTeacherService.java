package com.lxl.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxl.serviceedu.entity.EduTeacher;
import com.lxl.serviceedu.entity.vo.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lxl
 * @since 2021-12-31
 */
public interface EduTeacherService extends IService<EduTeacher> {
    void pageQuery(Page<EduTeacher> page, TeacherQuery teacherQuery);

    List<EduTeacher> getTeacherList();

    Map<String, Object> pageListWeb(Page<EduTeacher> pram);

    EduTeacher getTeacherById(String id);
}
