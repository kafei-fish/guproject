package com.lxl.serviceedu.controller;

import com.lxl.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/serviceedu/user")

public class EduLoginController {
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://pic3.zhimg.com/v2-e39e88eef5e214216a14fc5ff3d0e31e_r.jpg?source=1940ef5c");
    }
}
