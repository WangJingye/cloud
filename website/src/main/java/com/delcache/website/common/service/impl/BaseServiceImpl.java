package com.delcache.website.common.service.impl;

import com.delcache.website.common.domain.BaseEntity;
import com.delcache.website.common.mapper.BaseMapper;
import com.delcache.website.common.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BaseServiceImpl<T extends BaseEntity, T1 extends BaseMapper<T>> implements IService<T> {
    Class<T> clazz;

    @Autowired
    private T1 mapper;

    public List<T> findAll(Map<String, Object> params) {
        return mapper.selectList(params);
    }

    public T find(Map<String, Object> params) {
        return mapper.selectOne(params);
    }

    public Integer count(Map<String, Object> params) {
        return mapper.count(params);
    }

    public T save(Map<String, Object> params) {
        try {
            Type superclass = getClass().getGenericSuperclass();
            ParameterizedType parameterizedType = null;
            if (superclass instanceof ParameterizedType) {
                parameterizedType = (ParameterizedType) superclass;
                Type[] typeArray = parameterizedType.getActualTypeArguments();
                if (typeArray != null && typeArray.length > 0) {
                    clazz = (Class<T>) typeArray[0];
                }
            }
            T model = clazz.newInstance();
            model.loadData(params);
            return this.save(model);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public T save(T model) {
        int time = (int) (new Date().getTime() / 1000);
        if (model.getPrimaryKey() == 0) {
            model.setCreateTime(time);
            model.setUpdateTime(time);
            mapper.insert(model);
        } else {
            model.setUpdateTime(time);
            mapper.update(model);
        }
        return model;
    }
}
