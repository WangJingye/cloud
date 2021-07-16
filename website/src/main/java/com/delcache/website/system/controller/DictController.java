package com.delcache.website.system.controller;

import com.delcache.website.common.annotation.Authorize;
import com.delcache.website.common.controller.BaseController;
import com.delcache.website.common.domain.Admin;
import com.delcache.website.common.domain.DictData;
import com.delcache.website.common.domain.DictType;
import com.delcache.website.common.helper.Convert;
import com.delcache.website.common.domain.Dict;
import com.delcache.website.system.service.IDictDataService;
import com.delcache.website.system.service.IDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/dict")
public class DictController extends BaseController {

    @Autowired
    private IDictTypeService dictTypeService;
    @Autowired
    private IDictDataService dictDataService;

    @Authorize(value = "/system/dict/data")
    @RequestMapping("/data")
    public String getDataList(@RequestParam(required = false) Map<String, Object> params) {
        String type = request.getParameter("type");
        if (ObjectUtils.isEmpty(type)) {
            return error("参数有误");
        }
        params.put("dictType", type);
        return this.success(dictDataService.getList(params, true));
    }

    @RequestMapping("/type")
    public String getDictTypeList() {
        String type = request.getParameter("type");
        if (ObjectUtils.isEmpty(type)) {
            return error("参数有误");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("dictType", type);
        return this.success(dictDataService.getDataList(params));
    }

    @Authorize(value = "/system/dict/index")
    @RequestMapping(value = "/index")
    public String list(@RequestParam(required = false) Map<String, Object> params) {
        return success(dictTypeService.getList(params, true));
    }

    @Authorize(value = "/system/dict/setStatus")
    @RequestMapping(value = "/setStatus")
    public String setStatus(@RequestBody Map<String, Object> params) {
        Map<String, Object> search = new HashMap<>();
        long id = Convert.parseLong(params.get("id"));
        if (id == 0) {
            return error("参数有误");
        }
        if (ObjectUtils.isEmpty(params.get("status"))) {
            return error("参数有误");
        }
        search.put("id", id);
        if (dictTypeService.find(search) == null) {
            return error("参数有误");
        }
        int status = Convert.parseInt(params.get("status"));
        Map<String, Object> update = new HashMap<>();
        update.put("id", id);
        update.put("status", status);
        dictTypeService.save(update);
        return success("修改成功");
    }

    @Authorize(value = "/system/dict/setDataStatus")
    @RequestMapping(value = "/setDataStatus")
    public String setDataStatus(@RequestBody Map<String, Object> params) {
        Map<String, Object> search = new HashMap<>();
        long id = Convert.parseLong(params.get("id"));
        if (id == 0 || ObjectUtils.isEmpty(params.get("status"))) {
            return error("参数有误");
        }
        search.put("id", id);
        if (dictDataService.find(search) == null) {
            return error("参数有误");
        }
        int status = Convert.parseInt(params.get("status"));
        Map<String, Object> update = new HashMap<>();
        update.put("id", id);
        update.put("status", status);
        dictDataService.save(update);
        return success("修改成功");
    }

    @Authorize(value = "/system/dict/edit")
    @RequestMapping(value = "/edit")
    public String edit(@RequestBody(required = false) Map<String, Object> data) {
        if ("GET".equals(request.getMethod())) {
            long id = Convert.parseLong(request.getParameter("id"));
            Map<String, Object> result = new HashMap<>();
            if (id != 0) {
                Map<String, Object> search = new HashMap<>();
                search.put("id", id);
                DictType model = dictTypeService.find(search);
                result.put("id", model.getId());
                result.put("dictName", model.getDictName());
                result.put("dictType", model.getDictType());
                result.put("status", model.getStatus());
                result.put("remark", model.getRemark());
            }
            return success(result);
        } else {
            try {
                Admin user = (Admin) request.getAttribute("user");
                data.put("updateBy", user.getRealname());
                dictTypeService.saveDict(data);
            } catch (Exception e) {
                return error(e.getMessage());
            }
            return success("保存成功");
        }
    }

    @Authorize(value = "/system/dict/editData")
    @RequestMapping(value = "/editData")
    public String editData(@RequestBody(required = false) Map<String, Object> data) {
        if ("GET".equals(request.getMethod())) {
            long id = Convert.parseLong(request.getParameter("id"));
            Map<String, Object> result = new HashMap<>();
            if (id != 0) {
                Map<String, Object> search = new HashMap<>();
                search.put("id", id);
                DictData model = dictDataService.find(search);
                result.put("id", model.getId());
                result.put("dictLabel", model.getDictLabel());
                result.put("dictType", model.getDictType());
                result.put("dictValue", model.getDictValue());
                result.put("sort", model.getSort());
                result.put("status", model.getStatus());
                result.put("remark", model.getRemark());
            }
            return success(result);
        } else {
            try {
                Admin user = (Admin) request.getAttribute("user");
                data.put("updateBy", user.getRealname());
                dictDataService.saveData(data);
            } catch (Exception e) {
                return error(e.getMessage());
            }
            return success("保存成功");
        }
    }

}
