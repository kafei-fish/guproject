package com.lxl.ucenterservice.controller;

import com.google.gson.Gson;
import com.lxl.commonutils.JwtUtils;
import com.lxl.servicebase.exceptionhandler.GuliException;
import com.lxl.ucenterservice.entity.UcenterMember;
import com.lxl.ucenterservice.service.UcenterMemberService;
import com.lxl.ucenterservice.uitis.ConstantPropertiesUtil;
import com.lxl.ucenterservice.uitis.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;
    @RequestMapping("login")
    public String VxLogin() {

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }
        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "lixiaolong");
        return "redirect:"+qrcodeUrl;
    }
    @GetMapping("callback")
    public String callBack(String code,String state){
        try {
            //        System.out.println(code);
            //        System.out.println(state);
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);

            String result = null;

            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============" + result);

            //解析json字符串
            Gson gson = new Gson();
            HashMap map = gson.fromJson(result, HashMap.class);
            String accessToken = (String)map.get("access_token");
            String openid = (String)map.get("openid");
            UcenterMember Member=ucenterMemberService.getMenberByOpenId(openid);
            //如果为空就注册为新用户
            if(Member==null){
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);

                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");
                Member=new UcenterMember();
                Member.setOpenid(openid);
                Member.setNickname(nickname);
                Member.setAvatar(headimgurl);
                ucenterMemberService.save(Member);
            }
            String token = JwtUtils.getJwtToken(Member.getId(), Member.getNickname());
            return "redirect:http://localhost:3000?token=" + token;

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"登录失败");

        }

    }
}
