package com.lxl.ucenterservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginInfoVo {
    private String id;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "用户头像")
    private String avatar;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别 1 女，2 男")
    private Integer sex;
}
