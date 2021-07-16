package com.delcache.website.common.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.delcache.website.common.exception.AuthException;
import com.delcache.website.common.exception.LoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handlerException(Exception ex) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 0);
        if (ex instanceof LoginException) {
            result.put("code", 999);
        } else if (ex instanceof AuthException) {
            result.put("code", 998);
        }
        result.put("message", ex.getMessage());
        return JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }


}
