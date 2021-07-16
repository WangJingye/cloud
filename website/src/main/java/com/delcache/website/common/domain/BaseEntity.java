package com.delcache.website.common.domain;

import com.delcache.website.common.annotation.PrimaryKey;
import com.delcache.website.common.helper.Convert;
import com.delcache.website.common.helper.Util;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class BaseEntity {

    private Integer createTime;

    private Integer updateTime;

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public void loadData(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        Method[] methods = this.getClass().getMethods();
        Map<String, Method> methodMap = new HashMap<>();
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("set")) {
                String newName = name.substring(3);
                methodMap.put(newName.substring(0, 1).toLowerCase() + newName.substring(1), method);
            }
        }
        List<Field> fields = new ArrayList<>();
        Class tempClass= this.getClass();
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        Map<String, Field> fieldMap = new HashMap<>();
        for (Field f : fields) {
            fieldMap.put(f.getName(), f);
        }
        try {
            ConversionService conversionService = DefaultConversionService.getSharedInstance();
            for (Map.Entry<String, Object> item : map.entrySet()) {
                String key = Convert.underlineToCamel(item.getKey());
                if (!fieldMap.containsKey(key)) {
                    continue;
                }
                if (!methodMap.containsKey(key)) {
                    continue;
                }
                Field f = fieldMap.get(key);
                Method method = methodMap.get(key);
                method.invoke(this, conversionService.convert(item.getValue(), f.getType()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPrimaryKey(Object value) {
        Field[] fields = this.getClass().getDeclaredFields();
        Field key = null;
        for (Field field : fields) {
            if (field.getAnnotation(PrimaryKey.class) != null) {
                key = field;
                break;
            }
        }
        if (ObjectUtils.isEmpty(key)) {
            return;
        }
        String fun = "set" + key.getName().substring(0, 1).toUpperCase() + key.getName().substring(1);
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(fun)) {
                try {
                    ConversionService conversionService = DefaultConversionService.getSharedInstance();
                    method.invoke(this, conversionService.convert(value, key.getType()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public long getPrimaryKey() {
        Field[] fields = this.getClass().getDeclaredFields();
        Field key = null;
        for (Field field : fields) {
            if (field.getAnnotation(PrimaryKey.class) != null) {
                key = field;
                break;
            }
        }
        if (ObjectUtils.isEmpty(key)) {
            return 0;
        }
        String fun = "get" + key.getName().substring(0, 1).toUpperCase() + key.getName().substring(1);
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(fun)) {
                try {
                    Object id = method.invoke(this);
                    if (ObjectUtils.isEmpty(id)) {
                        return 0;
                    }
                    return (long) method.invoke(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return 0;
    }
}
