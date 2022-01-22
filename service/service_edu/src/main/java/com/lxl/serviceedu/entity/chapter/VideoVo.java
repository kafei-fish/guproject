package com.lxl.serviceedu.entity.chapter;

import lombok.Data;

import java.io.Serializable;

/**
 * @author MyLong
 */
@Data
public class VideoVo implements Serializable {
    private String id;
    private String title;
    private String videoSourceId;
}
