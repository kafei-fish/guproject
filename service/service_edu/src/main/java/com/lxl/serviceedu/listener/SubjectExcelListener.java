package com.lxl.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lxl.serviceedu.entity.excel.ExcelSubjectData;
import com.lxl.serviceedu.entity.EduSubject;
import com.lxl.serviceedu.service.EduSubjectService;

/**
 * 将excel表数据读入数据库中
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {
    private EduSubjectService eduSubjectService;
    public SubjectExcelListener() {
    }
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        //添加一级分类
        EduSubject eduOneSubject = this.existOneSubject(eduSubjectService, excelSubjectData.getOneSubjectName());
        if(eduOneSubject==null){
            eduOneSubject=new EduSubject();
            eduOneSubject.setTitle(excelSubjectData.getOneSubjectName());
            eduOneSubject.setParentId("0");
            eduSubjectService.save(eduOneSubject);
        }
        //获取一级分类id
        String id = eduOneSubject.getId();

        //添加二级分类
        EduSubject eduTwoSubject = this.existTwoSubject(eduSubjectService, excelSubjectData.getTwoSubjectName(),excelSubjectData.getTwoSubjectName());
        if(eduTwoSubject==null){
            eduTwoSubject=new EduSubject();
            eduTwoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            eduTwoSubject.setParentId(id);
            eduSubjectService.save(eduTwoSubject);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) { }

    /**
     *
     * @param subjectService
     * @param name
     * @return
     */
    public EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        return subjectService.getOne(wrapper);
    }
    /**
     *
     * @param subjectService
     * @param name
     * @return
     */
    public EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        return subjectService.getOne(wrapper);
    }
}
