package com.delcache.website.erp.controller;

import com.delcache.website.common.controller.BaseController;
import com.delcache.website.common.domain.SiteInfo;
import com.delcache.website.common.helper.Convert;
import com.delcache.website.erp.service.ISiteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/erp/siteInfo")
public class SiteInfoController extends BaseController {

    @Autowired
    ISiteInfoService siteInfoService;

    @RequestMapping("/getInfo")
    public String getInfo() {
        SiteInfo siteInfo = siteInfoService.find(null);
        String type = request.getParameter("type");
        if (ObjectUtils.isEmpty(type)) {
            return error("参数有误");
        }
        Map<String, Object> result = new HashMap<>();
        switch (type) {
            case "baseInfo":
                result.put("webIp", siteInfo.getWebIp());
                result.put("webHost", siteInfo.getWebHost());
                result.put("webName", siteInfo.getWebName());
                result.put("defaultPassword", siteInfo.getDefaultPassword());
                break;
            case "wechat":
                result.put("wechatAppId", siteInfo.getWechatAppId());
                result.put("wechatAppSecret", siteInfo.getWechatAppSecret());
                result.put("wechatMchId", siteInfo.getWechatMchId());
                result.put("wechatPayKey", siteInfo.getWechatPayKey());
                result.put("wechatNotify", siteInfo.getWechatNotify());
                break;
            case "index":
                result.put("webName", siteInfo.getWebName());
                break;
        }
        return success(result);
    }

    @RequestMapping("/saveInfo")
    public String saveInfo(@RequestBody Map<String, Object> params) {
        siteInfoService.saveInfo(params);
        return success("保存成功");
    }
}
