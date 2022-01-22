package com.lxl.serviceedu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("service-order")
@Component
public interface OrderClient {
    @GetMapping("/serviceorder/order/isBuyCourse/{memberId}/{id}")
    public boolean buyCourse(@PathVariable("memberId") String memberId, @PathVariable("id") String id);
}
