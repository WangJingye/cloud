package com.delcache.website.erp.service.impl;

import com.delcache.website.common.domain.SiteInfo;
import com.delcache.website.common.helper.Convert;
import com.delcache.website.common.service.impl.BaseServiceImpl;
import com.delcache.website.erp.mapper.SiteInfoMapper;
import com.delcache.website.erp.service.ISiteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SiteInfoServiceImpl extends BaseServiceImpl<SiteInfo, SiteInfoMapper> implements ISiteInfoService {

    @Autowired
    SiteInfoMapper siteInfoMapper;

    @Override
    public void saveInfo(Map<String, Object> params) {
        params.remove("id");
        SiteInfo info = this.find(null);
        if (info == null) {
            info = new SiteInfo();
        }
        info.loadData(params);
        this.save(info);
    }
}
