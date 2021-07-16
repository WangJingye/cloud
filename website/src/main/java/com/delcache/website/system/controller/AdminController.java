package com.delcache.website.system.controller;

import com.delcache.website.common.annotation.Authorize;
import com.delcache.website.common.controller.BaseController;
import com.delcache.website.common.domain.Admin;
import com.delcache.website.common.helper.Convert;
import com.delcache.website.system.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/system/admin")
public class AdminController extends BaseController {

    @Autowired
    private IAdminService adminService;

    @Authorize(value = "/system/admin/index")
    @RequestMapping(value = "/index")
    public String list(@RequestParam(required = false) Map<String, Object> params) {
        return success(adminService.getList(params, true));
    }

    @Authorize(value = "/system/admin/edit")
    @RequestMapping(value = "/edit")
    public String edit(@RequestBody(required = false) Map<String, Object> data, @RequestParam(value = "avatar", required = false) MultipartFile file) {
        if ("GET".equals(request.getMethod())) {
            long id = Convert.parseLong(request.getParameter("id"));
            Map<String, Object> result = new HashMap<>();
            if (id != 0) {
                Map<String, Object> search = new HashMap<>();
                search.put("adminId", id);
                Admin model = adminService.find(search);
                result.put("adminId", model.getAdminId());
                result.put("username", model.getUsername());
                result.put("realname", model.getRealname());
                result.put("email", model.getEmail());
                result.put("mobile", model.getMobile());
                result.put("avatar", model.getAvatar());
            }
            return success(result);
        } else {
            try {
                adminService.saveAdmin(data);
            } catch (Exception e) {
                return error(e.getMessage());
            }
            return success("保存成功");
        }
    }

    @Authorize(value = "/system/admin/setStatus")
    @RequestMapping(value = "/setStatus")
    public String setStatus(@RequestBody Map<String, Object> params) {
        Map<String, Object> search = new HashMap<>();
        long id = Convert.parseLong(params.get("id"));
        if (id == 0) {
            return error("参数有误");
        }
        search.put("adminId", id);
        Admin admin = adminService.find(search);
        if (admin == null) {
            return error("参数有误");
        }
        if (ObjectUtils.isEmpty(params.get("status"))) {
            return error("参数有误");
        }
        int status = Convert.parseInt(params.get("status"));
        Map<String, Object> update = new HashMap<>();
        update.put("adminId", id);
        update.put("status", status);
        adminService.save(update);
        return success("修改成功");
    }

    @Authorize(value = "/system/admin/resetPassword")
    @RequestMapping(value = "/resetPassword")
    public String resetPassword(@RequestBody Map<String, Object> params) {
        Map<String, Object> search = new HashMap<>();
        long id = Convert.parseLong(params.get("id"));
        if (id == 0) {
            return error("参数有误");
        }
        search.put("adminId", id);
        Admin admin = adminService.find(search);
        if (admin == null) {
            return error("参数有误");
        }
        adminService.changePassword(id, params.get("password").toString());
        return success("密码已重置");
    }

    @RequestMapping(value = "/changePassword")
    public String changePassword(@RequestBody Map<String, Object> params) {
        Map<String, Object> search = new HashMap<>();
        long id = Convert.parseLong(params.get("id"));
        if (id == 0) {
            return error("参数有误");
        }
        search.put("adminId", id);
        Admin admin = adminService.find(search);
        if (admin == null) {
            return error("参数有误");
        }
        adminService.changePassword(id, params.get("password").toString());
        return success("修改成功");
    }


    @RequestMapping(value = "/getAdminList")
    public String getAdminList(@RequestBody(required = false) Map<String, Object> params) {
        return success(adminService.getAdminList(params));
    }

}
