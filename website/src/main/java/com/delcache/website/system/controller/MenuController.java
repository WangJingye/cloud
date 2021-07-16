package com.delcache.website.system.controller;

import com.delcache.website.common.annotation.Authorize;
import com.delcache.website.common.controller.BaseController;
import com.delcache.website.common.domain.Admin;
import com.delcache.website.common.domain.Menu;
import com.delcache.website.common.helper.Convert;
import com.delcache.website.system.service.IMenuService;
import com.delcache.website.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleService roleService;

    @Authorize(value = "/system/menu/index")
    @RequestMapping(value = "/index")
    public String list(@RequestParam(required = false) Map<String, Object> params) {
        return success(menuService.getList(params, true));
    }

    @Authorize(value = "/system/menu/edit")
    @RequestMapping(value = "/edit")
    public String edit(@RequestBody(required = false) Map<String, Object> data) {
        if ("GET".equals(request.getMethod())) {
            String id = request.getParameter("id");
            Map<String, Object> search = new HashMap<>();
            search.put("id", id);
            Menu model = menuService.find(search);
            Map<String, Object> result = new HashMap<>();
            result.put("id", model.getId());
            result.put("parentId", model.getParentId());
            result.put("url", model.getUrl());
            result.put("name", model.getName());
            result.put("sort", model.getSort());
            result.put("icon", model.getIcon());
            result.put("desc", model.getDesc());
            return success(result);
        } else {
            try {
                menuService.saveMenu(data);
            } catch (Exception e) {
                return error(e.getMessage());
            }
            return success("保存成功");
        }
    }

    @RequestMapping(value = "/getSystemMenuList")
    public String getSystemMenuList() {
        Map<String, Object> search = new HashMap<>();
        search.put("orderBy", "sort desc,id asc");
        Admin user = (Admin) request.getAttribute("user");
        List<Long> menuIdList = roleService.getRoleMenuList(user.getAdminId(), null);
        List<Menu> list = menuService.findAll(search);
        if (user.getIdentity() == 0) {
            list = list.stream().filter(item -> menuIdList.contains(item.getId())).collect(Collectors.toList());
        }
        List<Menu> activeList = menuService.getActiveList(list, request.getParameter("url"));
        Map<String, Object> result = new HashMap<>();
        result.put("topList", menuService.getTopList(list));
        result.put("leftList", menuService.getLeftList(list, activeList));
        result.put("activeList", activeList);
        return success(result);
    }

    @RequestMapping(value = "/getActiveMenuList")
    public String getActiveMenuList() {
        List<Menu> list = menuService.findAll(null);
        List<Menu> activeList = menuService.getActiveList(list, request.getParameter("url"));
        return success(activeList);
    }

    @Authorize(value = "/system/menu/setStatus")
    @RequestMapping(value = "/setStatus")
    public String setStatus(@RequestBody Map<String, Object> params) {
        Map<String, Object> search = new HashMap<>();
        long id = Convert.parseLong(params.get("id"));
        if (id == 0) {
            return error("参数有误");
        }
        search.put("id", id);
        Menu menu = menuService.find(search);
        if (menu == null) {
            return error("参数有误");
        }
        if (ObjectUtils.isEmpty(params.get("status"))) {
            return error("参数有误");
        }
        int status = Convert.parseInt(params.get("status"));
        Map<String, Object> update = new HashMap<>();
        update.put("id", id);
        update.put("status", status);
        menuService.save(update);
        return success("修改成功");
    }

    @RequestMapping(value = "/getTreeList")
    public String getTreeList() {
        return success(menuService.getTreeList());
    }

    @RequestMapping(value = "/getAllMethodList")
    public String getAllMethodList() {
        long id = Convert.parseLong(request.getParameter("id"));
        return success(menuService.getAllMethodList(id));
    }
}
