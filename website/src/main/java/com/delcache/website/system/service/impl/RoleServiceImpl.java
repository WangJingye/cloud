package com.delcache.website.system.service.impl;

import com.delcache.website.common.domain.Role;
import com.delcache.website.common.domain.RoleMenu;
import com.delcache.website.common.helper.Convert;
import com.delcache.website.common.helper.Pager;
import com.delcache.website.common.helper.ParamsHelper;
import com.delcache.website.common.service.impl.BaseServiceImpl;
import com.delcache.website.system.mapper.RoleMapper;
import com.delcache.website.system.service.IAdminService;
import com.delcache.website.system.service.IRoleService;
import io.micrometer.core.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleMapper> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    WebApplicationContext applicationContext;

    @Override
    public Pager getList(Map<String, Object> params, boolean isPage) {
        params = new ParamsHelper(params).getParams();
        Map<String, Object> search = new HashMap<>();
        if (!ObjectUtils.isEmpty(params.get("status"))) {
            search.put("status", params.get("status"));
        }
        if (!ObjectUtils.isEmpty(params.get("id"))) {
            search.put("id", params.get("id"));
        }
        if (!ObjectUtils.isEmpty(params.get("url"))) {
            search.put("name", params.get("name"));
        }
        int total = this.count(search);
        if (isPage) {
            search.put("limit", params.get("limit"));
        }
        List<Role> list = this.findAll(search);
        return new Pager(list, total, params);
    }

    public void saveRole(Map<String, Object> params) throws Exception {
        this.save(params);
    }

    public List<Long> getRoleMenuList(long id, @Nullable Integer status) {
        return roleMapper.getRoleMenuList(id, status);
    }

    public void setRoleMenuList(long id, List<String> list, List<String> halfList) {
        List<String> existAllList = roleMapper.getRoleMenuList(id, 1).stream().map(String::valueOf).collect(Collectors.toList());
        List<String> existHalfList = roleMapper.getRoleMenuList(id, 2).stream().map(String::valueOf).collect(Collectors.toList());
        //删掉
        List<String> removeList = new ArrayList<>(existAllList);
        removeList.addAll(existHalfList);
        removeList.removeAll(list);
        removeList.removeAll(halfList);

        halfList.removeAll(existHalfList);
        //添加
        List<String> addHalfList = new ArrayList<>(halfList);
        addHalfList.removeAll(existAllList);
        //修改
        halfList.removeAll(addHalfList);

        list.removeAll(existAllList);
        //添加
        List<String> addAllList = new ArrayList<>(list);
        addAllList.removeAll(existHalfList);
        //修改
        list.removeAll(addAllList);

        int time = (int) (new Date().getTime() / 1000);
        if (addAllList.size() > 0) {
            for (String menuId : addAllList) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(id);
                roleMenu.setStatus(1);
                roleMenu.setMenuId(Convert.parseLong(menuId));
                roleMenu.setCreateTime(time);
                roleMapper.saveRoleMenu(roleMenu);
            }
        }
        if (list.size() > 0) {
            roleMapper.updateRoleMenus(id, list, 1);
        }
        if (addHalfList.size() > 0) {
            for (String menuId : addHalfList) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(id);
                roleMenu.setStatus(2);
                roleMenu.setMenuId(Convert.parseLong(menuId));
                roleMenu.setCreateTime(time);
                roleMapper.saveRoleMenu(roleMenu);
            }
        }
        if (halfList.size() > 0) {
            roleMapper.updateRoleMenus(id, halfList, 2);
        }
        if (removeList.size() > 0) {
            roleMapper.deleteRoleMenus(id, removeList);
        }
    }

    @Override
    public List<Long> getRoleAdminList(long id) {
        return roleMapper.getRoleAdminList(id);
    }

    @Override
    public void setRoleAdminList(long id, List<String> list) {
        List<String> existList = roleMapper.getRoleAdminList(id).stream().map(String::valueOf).collect(Collectors.toList());
        List<String> removeList = new ArrayList<>(existList);
        removeList.removeAll(list);
        list.removeAll(existList);
        if (list.size() > 0) {
            roleMapper.addRoleAdminList(id, list);
        }
        if (removeList.size() > 0) {
            roleMapper.deleteRoleAdminList(id, removeList);
        }
    }
}
