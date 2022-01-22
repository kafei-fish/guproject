package com.lxl.servicecms.controller;


import com.lxl.commonutils.R;
import com.lxl.servicecms.entity.CrmBanner;
import com.lxl.servicecms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前台banner管理接口
 * </p>
 *
 * @author lxl
 * @since 2022-01-11
 */
@RestController
@RequestMapping("/servicecms/crm-banner")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;
    @GetMapping("getAllBanner")
    public R getAllBanner(){

        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}

