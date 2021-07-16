package com.delcache.website.system.mapper;

import com.delcache.website.common.domain.Menu;
import com.delcache.website.common.mapper.BaseMapper;

import java.util.Map;

public interface MenuMapper extends BaseMapper<Menu> {

    public Integer checkUserAuth(Map<String, Object> parmas);
}
