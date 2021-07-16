package com.delcache.website.common.mapper;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

    public List<T> selectList(Map<String, Object> params);

    public T selectOne(Map<String, Object> params);

    public Integer count(Map<String, Object> params);

    public void insert(T model);

    public void update(T model);
}
