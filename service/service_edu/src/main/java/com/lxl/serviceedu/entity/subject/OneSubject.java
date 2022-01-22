package com.lxl.serviceedu.entity.subject;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MyLong
 * 一级分类
 */
@Data
public class OneSubject  implements Serializable {
    private String id;
    private String title;
    private List<TwoSubject> children=new ArrayList<>();
}
