package com.lxl.servicevido.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.lxl.servicebase.exceptionhandler.GuliException;
import com.lxl.servicevido.service.VideoService;
import com.lxl.servicevido.uitis.AliyunVodSDKUtils;
import com.lxl.servicevido.uitis.VideoPropertiesUitil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author MyLong
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {
    @Override
    public String addVideo(MultipartFile file) {
        try {
            String filename=file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            String title=filename.substring(0,filename.lastIndexOf("."));
            UploadStreamRequest request=new UploadStreamRequest(VideoPropertiesUitil.ACCESS_KEY_ID,VideoPropertiesUitil.ACCESS_KEY_SECRET,title,filename,inputStream);
            UploadVideoImpl uploadVideo=new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(request);
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                log.warn(errorMessage);
                if(StringUtils.isEmpty(videoId)){
                    throw new GuliException(20001, errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001,"视频上传失败");
        }
    }

    @Override
    public void romveVideo(String id) {
        try {
            DefaultAcsClient client= AliyunVodSDKUtils.initVodClient(VideoPropertiesUitil.ACCESS_KEY_ID,VideoPropertiesUitil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request=new DeleteVideoRequest();
            request.setVideoIds(id);
            DeleteVideoResponse response=client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"视频删除失败");
        }


    }

    @Override
    public void removeVideoByCourseId(List<String> videoIdList) {
        try {
            DefaultAcsClient client= AliyunVodSDKUtils.initVodClient(VideoPropertiesUitil.ACCESS_KEY_ID,VideoPropertiesUitil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request=new DeleteVideoRequest();
            String videoIds = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(videoIds);
            DeleteVideoResponse response=client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"视频删除失败");
        }
    }
}
