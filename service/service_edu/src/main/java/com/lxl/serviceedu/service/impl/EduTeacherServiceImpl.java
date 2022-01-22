package com.lxl.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxl.serviceedu.entity.EduTeacher;
import com.lxl.serviceedu.mapper.EduTeacherMapper;
import com.lxl.serviceedu.service.EduTeacherService;
import com.lxl.serviceedu.entity.vo.TeacherQuery;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    @Cacheable(value = "teacher",key = "'getTeacherList'")
    public List<EduTeacher> getTeacherList() {
        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 4");

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduTeacher> pram) {
        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("id");

        baseMapper.selectPage(pram, queryWrapper);
        List<EduTeacher> records = pram.getRecords();
        long total = pram.getTotal();
        long pages = pram.getPages();
        long current = pram.getCurrent();
        long size = pram.getSize();
        boolean hasNext = pram.hasNext();
        boolean hasPrevious = pram.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public EduTeacher getTeacherById(String id) {

        return baseMapper.selectById(id);
    }
}
