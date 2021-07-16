package com.delcache.website.erp.service;

import com.delcache.website.common.domain.SiteInfo;
import com.delcache.website.common.service.IService;

import java.util.Map;

public interface ISiteInfoService extends IService<SiteInfo> {
    public void saveInfo(Map<String,Object> params);
}
