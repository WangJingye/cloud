package com.delcache.website.system.mapper;

import com.delcache.website.common.domain.Admin;
import com.delcache.website.common.domain.Dict;
import com.delcache.website.common.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface AdminMapper extends BaseMapper<Admin> {

    public List<Dict> getAdminList(Map<String, Object> params);

}
