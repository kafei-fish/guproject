package com.lxl.servicemsm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.lxl.servicemsm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    /**
     * 发送短信
     * @param phone 手机含
     * @param param 验证码
     * @return
     */
    @Override
    public boolean send(String phone,   Map<String,Object> param) {
        if(StringUtils.isEmpty(phone)) {return false;}
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "LTAI5tRx6drjf2jVDLpdXnHu", "JuXrfAnIe23OFm19fT1MfFoIE0VGHE");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("SignName", "阿里云短信测试");
        request.putQueryParameter("TemplateCode", "SMS_154950909");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证码的数据
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        }  catch (ClientException e) {
            e.printStackTrace();
            return false;
        }

    }

}
