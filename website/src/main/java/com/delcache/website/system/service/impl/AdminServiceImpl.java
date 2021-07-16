package com.delcache.website.system.service.impl;

import com.delcache.website.common.domain.Admin;
import com.delcache.website.common.domain.Dict;
import com.delcache.website.common.helper.Convert;
import com.delcache.website.common.helper.Encrypt;
import com.delcache.website.common.helper.Pager;
import com.delcache.website.common.helper.ParamsHelper;
import com.delcache.website.common.service.impl.BaseServiceImpl;
import com.delcache.website.system.mapper.AdminMapper;
import com.delcache.website.system.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, AdminMapper> implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Pager getList(Map<String, Object> params, boolean isPage) {
        params = new ParamsHelper(params).getParams();
        Map<String, Object> search = new HashMap<>();
        if (!ObjectUtils.isEmpty(params.get("adminId"))) {
            search.put("adminId", params.get("adminId"));
        }
        if (!ObjectUtils.isEmpty(params.get("username"))) {
            search.put("username_like", params.get("username"));
        }
        if (!ObjectUtils.isEmpty(params.get("realname"))) {
            search.put("realname_like", params.get("realname"));
        }
        if (!ObjectUtils.isEmpty(params.get("status"))) {
            search.put("status", params.get("status"));
        }
        int total = this.count(search);
        if (isPage) {
            search.put("limit", params.get("limit"));
        }
        List<Admin> list = this.findAll(search);
        return new Pager(list, total, params);
    }

    public void changePassword(long id, String password) {
        String salt = String.format("%04d", (int) (Math.random() * 9999));
        password = Encrypt.encryptPassword(password, salt);
        Map<String, Object> update = new HashMap<>();
        update.put("adminId", id);
        update.put("password", password);
        update.put("salt", salt);
        this.save(update);
    }

    public void saveAdmin(Map<String, Object> params) throws Exception {
        Long id = Convert.parseLong(params.getOrDefault("adminId", "0").toString());
        String username = params.get("username").toString();
        Map<String, Object> check = new HashMap<>();
        check.put("username", username);
        if (id == 0) {
            String salt = String.format("%04d", (int) (Math.random() * 9999));
            String password = Encrypt.encryptPassword("123456", salt);
            params.put("password", password);
            params.put("salt", salt);
        } else {
            check.put("condition", "admin_id!=" + id);
        }
        if (this.count(check) > 0) {
            throw new Exception("用户名不能重复");
        }
        this.save(params);
    }

    public Map<String,String> getAdminList(Map<String, Object> map) {
        return adminMapper.getAdminList(map).stream().collect(Collectors.toMap(Dict::getValue,Dict::getTitle,(oldVal, currVal) -> oldVal, LinkedHashMap::new));
    }
}
