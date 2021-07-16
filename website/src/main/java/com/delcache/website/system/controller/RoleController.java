package com.delcache.website.system.controller;

import com.delcache.website.common.annotation.Authorize;
import com.delcache.website.common.controller.BaseController;
import com.delcache.website.common.domain.Role;
import com.delcache.website.common.helper.Convert;
import com.delcache.website.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @Authorize(value = "/system/role/index")
    @RequestMapping(value = "/index")
    public String list(@RequestParam(required = false) Map<String, Object> params) {
        return success(roleService.getList(params, true));
    }

    @Authorize(value = "/system/role/edit")
    @RequestMapping(value = "/edit")
    public String edit(@RequestBody(required = false) Map<String, Object> data, @RequestParam(value = "avatar", required = false) MultipartFile file) {
        if ("GET".equals(request.getMethod())) {
            String id = request.getParameter("id");
            Map<String, Object> search = new HashMap<>();
            search.put("id", id);
            Role model = roleService.find(search);
            Map<String, Object> result = new HashMap<>();
            result.put("id", model.getId());
            result.put("name", model.getName());
            result.put("desc", model.getDesc());
            return success(result);
        } else {
            try {
                roleService.saveRole(data);
            } catch (Exception e) {
                return error(e.getMessage());
            }
            return success("保存成功");
        }
    }

    @Authorize(value = "/system/role/setStatus")
    @RequestMapping(value = "/setStatus")
    public String setStatus(@RequestBody Map<String, Object> params) {
        Map<String, Object> search = new HashMap<>();
        long id = Convert.parseLong(params.get("id"));
        if (id == 0) {
            return error("参数有误");
        }
        search.put("id", id);
        Role role = roleService.find(search);
        if (role == null) {
            return error("参数有误");
        }
        if (ObjectUtils.isEmpty(params.get("status"))) {
            return error("参数有误");
        }
        int status = Convert.parseInt(params.get("status"));
        Map<String, Object> update = new HashMap<>();
        update.put("id", id);
        update.put("status", status);
        roleService.save(update);
        return success("修改成功");
    }

    @Authorize(value = "/system/role/setRoleMenu")
    @RequestMapping(value = "/setRoleMenu")
    public String setRoleMenu(@RequestBody(required = false) Map<String, Object> params) {
        if ("GET".equals(request.getMethod())) {
            long id = Convert.parseLong(request.getParameter("id"));
            if (id == 0) {
                return error("参数有误");
            }
            Map<String, Object> search = new HashMap<>();
            search.put("id", id);
            Role model = roleService.find(search);
            List<Long> menuList = roleService.getRoleMenuList(id, 1);
            List<Long> halfList = roleService.getRoleMenuList(id, 2);
            Map<String, Object> result = new HashMap<>();
            result.put("id", model.getId());
            result.put("name", model.getName());
            result.put("menuList", menuList.stream().map(String::valueOf).collect(Collectors.toList()));
            result.put("halfList", halfList.stream().map(String::valueOf).collect(Collectors.toList()));
            return success(result);
        } else {
            long id = Convert.parseLong(params.get("id"));
            if (id == 0) {
                return error("参数有误");
            }
            List<String> menuList = (List<String>) params.getOrDefault("menuList", new ArrayList<>());
            List<String> halfList = (List<String>) params.getOrDefault("halfList", new ArrayList<>());
            roleService.setRoleMenuList(id, menuList, halfList);
            return success("保存成功");
        }
    }

    @Authorize(value = "/system/role/setRoleAdmin")
    @RequestMapping(value = "/setRoleAdmin")
    public String setRoleAdmin(@RequestBody(required = false) Map<String, Object> params) {
        if ("GET".equals(request.getMethod())) {
            long id = Convert.parseLong(request.getParameter("id"));
            if (id == 0) {
                return error("参数有误");
            }
            Map<String, Object> search = new HashMap<>();
            search.put("id", id);
            Role model = roleService.find(search);
            List<Long> adminList = roleService.getRoleAdminList(model.getId());
            Map<String, Object> result = new HashMap<>();
            result.put("id", model.getId());
            result.put("name", model.getName());
            result.put("adminList",adminList);
            return success(result);
        } else {
            long id = Convert.parseLong(params.get("id"));
            if (id == 0) {
                return error("参数有误");
            }
            List<String> adminList = (List<String>) params.getOrDefault("adminList", new ArrayList<>());
            roleService.setRoleAdminList(id, adminList);
            return success("保存成功");
        }
    }
}
