package com.lxl.serviceedu.client;

import com.lxl.serviceedu.entity.UcenterMember;
import com.lxl.serviceedu.entity.vo.MemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@FeignClient("service-ucenter")
@Component
public interface UcClient {
    //使用nacos远程调用
    @GetMapping("/ucenterservice/ucenter-member/parseToken")
    public UcenterMember parseToken(HttpServletRequest request);
    @PostMapping("/ucenterservice/ucenter-member/getMenber/{memberId}")
    public MemberVo getMenber(@PathVariable("memberId") String memberId);
    @GetMapping("/serviceorder/order/isBuyCourse")
    public boolean buyCourse(@PathVariable("memberId") String memberId,@PathVariable("id") String id);

}
