package com.delcache.website.system.mapper;

import com.delcache.website.common.domain.DictData;
import com.delcache.website.common.domain.Dict;
import com.delcache.website.common.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface DictDataMapper extends BaseMapper<DictData> {

    public List<Dict> getDataList(Map<String, Object> params);
}
