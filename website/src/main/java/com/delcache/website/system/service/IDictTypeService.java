package com.delcache.website.system.service;

import com.delcache.website.common.domain.DictType;
import com.delcache.website.common.helper.Pager;
import com.delcache.website.common.service.IService;

import java.util.Map;

public interface IDictTypeService extends IService<DictType> {

    public Pager getList(Map<String, Object> params, boolean isPage);

    public void saveDict(Map<String, Object> params) throws Exception;
}
