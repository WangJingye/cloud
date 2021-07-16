package com.delcache.website.system.service;

import com.delcache.website.common.domain.Admin;
import com.delcache.website.common.domain.Dict;
import com.delcache.website.common.helper.Pager;
import com.delcache.website.common.service.IService;

import java.util.List;
import java.util.Map;

public interface IAdminService extends IService<Admin> {

    public void changePassword(long id, String password);

    public void saveAdmin(Map<String, Object> params) throws Exception;

    public Pager getList(Map<String, Object> params, boolean isPage);

    public Map<String,String> getAdminList(Map<String, Object> map);
}
