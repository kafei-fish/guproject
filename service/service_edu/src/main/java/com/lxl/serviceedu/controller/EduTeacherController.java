package com.lxl.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxl.commonutils.R;
import com.lxl.serviceedu.entity.EduTeacher;
import com.lxl.serviceedu.entity.vo.TeacherQuery;
import com.lxl.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lxl
 * @since 2021-12-31
 */
@Api(description = "讲师管理")
@RestController

@RequestMapping("/serviceedu/edu-teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    /**
     * 获取教师全部信息
     * @return 教师信息
     */
    @ApiOperation(value = "讲师列表")
    @GetMapping("getEduTeacher")
    public R list(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    /**
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据Id删除教师")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name = "id" ,value = "讲师ID",required = true)
            @PathVariable String id){
        boolean flag= teacherService.removeById(id);
        if(flag){
            return R.ok();
        }
        return R.error();
    }

    /**
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page",value = "当前页",required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable Long limit){
        Page<EduTeacher> pageTeacher=new Page<>(page,limit);
       teacherService.page(pageTeacher, null);
       //总记录数
        long total = pageTeacher.getTotal();
        //分页的数据
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    /**
     *
     * @param page
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "分页查询接口")
    @GetMapping("pageTeacherCondition/{page}/{limit}}")
    public R pageQueryList(
            @ApiParam(name = "page",value = "当前页面",required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "当前页面条数",required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherQuery",value = "查询对象",required = false)
            TeacherQuery teacherQuery){
        Page<EduTeacher> pageQuery =new Page<>(page,limit);
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
       //
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String end = teacherQuery.getEnd();
        String begin = teacherQuery.getBegin();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        teacherService.page(pageQuery,wrapper);
        long total = pageQuery.getTotal();
        List<EduTeacher> records = pageQuery.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    /**
     *
     * @param eduTeacher
     * @return
     */
    @ApiOperation(value = "添加教师接口")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        return teacherService.save(eduTeacher) ? R.ok():R.error();
    }

    /**
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据Id查询教师接口")
    @GetMapping("{id}")
    public R getTeacher(
            @ApiParam(name = "id",value = "教师ID",required = true)
            @PathVariable Long id){
        EduTeacher byId = teacherService.getById(id);
        return R.ok().data("item",byId);
    }

    /**
     *
     * @param eduTeacher
     * @return
     */
    @ApiOperation(value = "根据ID修改")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        return  teacherService.updateById(eduTeacher) ? R.ok() : R.error();
    }
}

