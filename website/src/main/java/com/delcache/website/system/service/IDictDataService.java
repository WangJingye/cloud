package com.delcache.website.system.service;

import com.delcache.website.common.domain.Dict;
import com.delcache.website.common.domain.DictData;
import com.delcache.website.common.helper.Pager;
import com.delcache.website.common.service.IService;

import java.util.List;
import java.util.Map;

public interface IDictDataService extends IService<DictData> {

    public Pager getList(Map<String, Object> params, boolean isPage);

    public List<Dict> getDataList(Map<String, Object> params);

    public void saveData(Map<String, Object> params) throws Exception;
}
