package com.lxl.serviceorde.client;

import com.lxl.commonutils.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
@Component
public interface UcClient {
    @GetMapping("/ucenterservice/ucenter-member/getMemberVo/{memberId}")
    public UcenterMemberVo getMemberVo(@PathVariable("memberId") String memberId);
}
