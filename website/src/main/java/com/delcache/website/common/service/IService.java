package com.delcache.website.common.service;

import java.util.List;
import java.util.Map;

public interface IService<T> {

    public List<T> findAll(Map<String, Object> params);

    public T find(Map<String, Object> params);

    public Integer count(Map<String, Object> parmas);

    public T save(Map<String, Object> params);

    public T save(T model);
}
