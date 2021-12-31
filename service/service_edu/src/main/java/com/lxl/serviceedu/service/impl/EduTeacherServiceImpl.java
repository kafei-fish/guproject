package com.lxl.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxl.serviceedu.entity.EduTeacher;
import com.lxl.serviceedu.entity.vo.TeacherQuery;
import com.lxl.serviceedu.mapper.EduTeacherMapper;
import com.lxl.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lxl
 * @since 2021-12-31
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> page, TeacherQuery teacherQuery) {
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        if(teacherQuery==null){
            baseMapper.selectPage(page,wrapper);
            return;
        }
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String end = teacherQuery.getEnd();
        String begin = teacherQuery.getBegin();
        if(StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        baseMapper.selectPage(page,wrapper);
    }
}
