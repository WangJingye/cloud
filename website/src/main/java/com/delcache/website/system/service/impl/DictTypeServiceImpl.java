package com.delcache.website.system.service.impl;

import com.delcache.website.common.domain.DictType;
import com.delcache.website.common.helper.Convert;
import com.delcache.website.common.helper.Pager;
import com.delcache.website.common.helper.ParamsHelper;
import com.delcache.website.common.service.impl.BaseServiceImpl;
import com.delcache.website.system.mapper.DictTypeMapper;
import com.delcache.website.system.service.IDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictTypeServiceImpl extends BaseServiceImpl<DictType, DictTypeMapper> implements IDictTypeService {

    @Autowired
    private DictTypeMapper dictTypeMapper;

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
        List<DictType> list = this.findAll(search);
        return new Pager(list, total, params);
    }

    @Override
    public void saveDict(Map<String, Object> params)  throws Exception{
        Long id = Convert.parseLong(params.get("id"));
        String dictType = params.get("dictType").toString();
        Map<String, Object> check = new HashMap<>();
        check.put("dictType", dictType);
        if (id == 0) {
            check.put("createBy", check.get("updateBy"));
        } else {
            check.remove("createBy");
            check.put("condition", "id!=" + id);
        }
        if (this.count(check) > 0) {
            throw new Exception("字典类型不能重复");
        }
        this.save(params);
    }
}
