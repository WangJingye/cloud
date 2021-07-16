package com.delcache.website.system.service.impl;

import com.delcache.website.common.domain.Dict;
import com.delcache.website.common.domain.DictData;
import com.delcache.website.common.helper.Pager;
import com.delcache.website.common.helper.ParamsHelper;
import com.delcache.website.common.service.impl.BaseServiceImpl;
import com.delcache.website.system.mapper.DictDataMapper;
import com.delcache.website.system.service.IDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictDataServiceImpl extends BaseServiceImpl<DictData, DictDataMapper> implements IDictDataService {

    @Autowired
    private DictDataMapper dictDataMapper;

    @Override
    public Pager getList(Map<String, Object> params, boolean isPage) {
        params = new ParamsHelper(params).getParams();
        Map<String, Object> search = new HashMap<>();
        if (!ObjectUtils.isEmpty(params.get("id"))) {
            search.put("id", params.get("id"));
        }
        if (!ObjectUtils.isEmpty(params.get("dictName"))) {
            search.put("dictName", params.get("dictName"));
        }
        if (!ObjectUtils.isEmpty(params.get("dictType"))) {
            search.put("dictType", params.get("dictType"));
        }
        if (!ObjectUtils.isEmpty(params.get("status"))) {
            search.put("status", params.get("status"));
        }
        int total = this.count(search);
        if (isPage) {
            search.put("limit", params.get("limit"));
        }
        List<DictData> list = this.findAll(search);
        return new Pager(list, total, params);
    }

    public List<Dict> getDataList(Map<String, Object> params) {
        return dictDataMapper.getDataList(params);
    }

    @Override
    public void saveData(Map<String, Object> params) throws Exception {
        this.save(params);
    }
}
