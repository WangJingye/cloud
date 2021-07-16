package com.delcache.website.system.controller;

import com.delcache.website.common.controller.BaseController;
import com.delcache.website.common.domain.Admin;
import com.delcache.website.common.helper.Encrypt;
import com.delcache.website.system.service.IAdminService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/system/public")
public class PublicController extends BaseController {

    @Autowired
    private IAdminService adminService;

    @RequestMapping("/upload")
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file == null) {
            return error("文件未上传");
        }
        System.out.println(file.getContentType());//在控制台打印文件的类型
        System.out.println(file.getName());//返回文件的名称
        System.out.println(file.getOriginalFilename());//返回文件的原文件名
        String[] exts = {"gif", "jpg", "jpeg", "png", "bmp", "mp4"};
        String[] fileArr = file.getOriginalFilename().split("\\.");
        String ext = fileArr[fileArr.length - 1];
        if (!Arrays.asList(exts).contains(ext)) {
            return error("文件类型有误");
        }
        Map<String, String> result = new HashMap<>();
        try {
            String path = ResourceUtils.getURL("classpath:").getPath();
            System.out.println(path);//在控制台打印文件的类型
            String filename = "upload/" + DigestUtils.md5Hex(file.getInputStream()) + "." + ext;
            file.transferTo(new File("D:/phpstudy_pro/WWW/" + filename));
            result.put("url", filename);
        } catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
        return success(result);
    }

    @RequestMapping("/login")
    public String login(@RequestBody Map<String, Object> params) {
        try {
            if (ObjectUtils.isEmpty(params.get("username"))) {
                throw new Exception("用户名不能为空");
            }
            if (ObjectUtils.isEmpty(params.get("password"))) {
                throw new Exception("密码不能为空");
            }
            Map<String, Object> search = new HashMap<>();
            search.put("username", params.get("username"));
            Admin admin = adminService.find(search);
            if (admin == null) {
                throw new Exception("用户名密码有误");
            }
            if (!Encrypt.encryptPassword(params.get("password").toString(), admin.getSalt()).equals(admin.getPassword())) {
                throw new Exception("用户名密码有误");
            }
            String token = DigestUtils.md5Hex(UUID.randomUUID().toString());
            admin.setToken(token);
            admin.setLastLoginTime((int) (new Date().getTime() / 1000));
            adminService.save(admin);
            Map<String, Object> result = new HashMap<>();
            result.put("adminId", admin.getAdminId());
            result.put("username", admin.getUsername());
            result.put("realname", admin.getRealname());
            result.put("email", admin.getEmail());
            result.put("mobile", admin.getMobile());
            result.put("avatar", admin.getAvatar());
            result.put("identity", admin.getIdentity());
            result.put("token", admin.getToken());
            return success(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @RequestMapping("/logout")
    public String logout() {
        Admin user = (Admin) request.getAttribute("user");
        user.setToken("");
        adminService.save(user);
        return success();
    }
}