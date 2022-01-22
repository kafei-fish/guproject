package com.lxl.serviceoss.controller;

import com.lxl.commonutils.R;
import com.lxl.serviceoss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Api(description="阿里云文件管理")
@RestController
@RequestMapping("/eduoss/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @PostMapping("upload")
    @ApiOperation(value = "文件上传")
    public R uploadFile(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file){
        String url=fileService.upload(file);
        return R.ok().data("url",url);
    }
}
