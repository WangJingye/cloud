package com.delcache.website.system.service;


import com.delcache.website.common.domain.Role;
import com.delcache.website.common.helper.Pager;
import com.delcache.website.common.service.IService;
import io.micrometer.core.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface IRoleService extends IService<Role> {

    public Pager getList(Map<String, Object> params, boolean isPage);

    public void saveRole(Map<String, Object> params) throws Exception;

    public List<Long> getRoleMenuList(long id,@Nullable Integer status);

    public void setRoleMenuList(long id, List<String> list, List<String> halfList);

    public List<Long> getRoleAdminList(long id);

    public void setRoleAdminList(long id, List<String> list);
}
