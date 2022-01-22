package com.lxl.servicevido.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lxl.commonutils.R;
import com.lxl.servicebase.exceptionhandler.GuliException;
import com.lxl.servicevido.service.VideoService;
import com.lxl.servicevido.uitis.AliyunVodSDKUtils;
import com.lxl.servicevido.uitis.VideoPropertiesUitil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author MyLong
 */
@RestController
@RequestMapping("/servicevido/vido")
public class VideoController {
    @Autowired
    private VideoService  videoService;
    @PostMapping("addVideo")
    public R addVideo(MultipartFile file){
        String id=videoService.addVideo(file);
        return R.ok().data("id",id);
    }
    @DeleteMapping("deleteVideo/{id}")
    public R romveVideo(@PathVariable String id){
        videoService.romveVideo(id);
        return R.ok();
    }
    @DeleteMapping("removeVideoByCourseId")
    public R removeVideoByCourseId(@RequestParam("videoIdList") List<String> videoIdList){
        videoService.removeVideoByCourseId(videoIdList);
        return R.ok();
    }
    @GetMapping("getVideoPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable String videoId ){
        try {
            //初始化
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient( VideoPropertiesUitil.ACCESS_KEY_ID, VideoPropertiesUitil.ACCESS_KEY_SECRET);

            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);

            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //得到播放凭证
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"获取凭证失败");
        }


    }
}
