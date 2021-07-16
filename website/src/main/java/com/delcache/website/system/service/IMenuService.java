package com.delcache.website.system.service;


import com.delcache.website.common.domain.Admin;
import com.delcache.website.common.domain.Menu;
import com.delcache.website.common.domain.Dict;
import com.delcache.website.common.helper.Pager;
import com.delcache.website.common.service.IService;

import java.util.List;
import java.util.Map;

public interface IMenuService extends IService<Menu> {

    public List<Menu> getTopList(List<Menu> list);

    public List<Map<String, Object>> getLeftList(List<Menu> list, List<Menu> activeMenuList);

    public List<Menu> getActiveList(List<Menu> list, String url);

    public boolean checkUserAuth(Admin admin, Menu menu);

    public Pager getList(Map<String, Object> params, boolean isPage);

    public List<Dict> getTreeList();

    public List<String> getAllMethodList(long id);

    public void saveMenu(Map<String, Object> params) throws Exception;
}
