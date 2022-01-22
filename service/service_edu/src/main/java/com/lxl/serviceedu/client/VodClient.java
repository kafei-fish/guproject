package com.lxl.serviceedu.client;

import com.lxl.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-vod")
@Component
public interface VodClient {
    @DeleteMapping(value = "/servicevido/vido/deleteVideo/{id}")
    public R romveVideo(@PathVariable("id") String id);

    @DeleteMapping("/servicevido/vido/removeVideoByCourseId")
    public R removeVideoByCourseId(@RequestParam("videoIdList") List<String> videoIdList);
}
