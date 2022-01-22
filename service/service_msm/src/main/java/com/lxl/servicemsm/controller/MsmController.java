package com.lxl.servicemsm.controller;

import com.lxl.commonutils.R;
import com.lxl.servicemsm.service.MsmService;
import com.lxl.servicemsm.uitis.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/servicemsm/msm")
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String ,String> redisTemplate;
    @GetMapping("send/{phone}")
    public R sendShortMessage(@PathVariable String phone){
        //加上reids，将手机哈存入reids缓存中
        String code = redisTemplate.opsForValue().get(phone);

        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        code= RandomUtil.getFourBitRandom();
        System.out.println("短信验证码"+code);
        Map<String,Object> param=new HashMap<>();
        param.put("code",code);
            //如果发送成功，将验证码放入到reids缓存中，并且设置有效使劲按，设置redis的有效时间
        redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
        return R.ok();


    }
}
