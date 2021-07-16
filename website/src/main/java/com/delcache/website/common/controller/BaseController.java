package com.delcache.website.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    @Autowired
    public HttpServletRequest request;


    public String success(String message, Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", message);
        result.put("data", data);
        return JSONObject.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
    }

    public String success() {
        return this.success("", null);
    }

    public String success(Object data) {
        if ("java.lang.String".equals(data.getClass().getTypeName())) {
            return this.success(data.toString(), null);
        }
        return this.success("", data);
    }

    public String error(String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("message", message);
        return JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }
}
