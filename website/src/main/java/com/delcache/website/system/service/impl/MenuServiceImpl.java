package com.delcache.website.system.service.impl;

import com.delcache.website.common.annotation.Authorize;
import com.delcache.website.common.domain.Admin;
import com.delcache.website.common.domain.Menu;
import com.delcache.website.common.domain.Dict;
import com.delcache.website.common.helper.Pager;
import com.delcache.website.common.helper.ParamsHelper;
import com.delcache.website.common.helper.Util;
import com.delcache.website.common.service.impl.BaseServiceImpl;
import com.delcache.website.system.mapper.MenuMapper;
import com.delcache.website.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, MenuMapper> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    WebApplicationContext applicationContext;

    @Override
    public Pager getList(Map<String, Object> params, boolean isPage) {
        params = new ParamsHelper(params).getParams();
        Map<String, Object> search = new HashMap<>();
        if (!ObjectUtils.isEmpty(params.get("status"))) {
            search.put("status", params.get("status"));
        }
        if (!ObjectUtils.isEmpty(params.get("name"))) {
            search.put("name", params.get("name"));
        }
        if (!ObjectUtils.isEmpty(params.get("url"))) {
            search.put("url", params.get("url"));
        }
        int total = this.count(search);
        if (isPage) {
            search.put("limit", params.get("limit"));
        }
        List<Menu> list = this.findAll(search);
        return new Pager(list, total, params);
    }

    @Override
    public List<Menu> getTopList(List<Menu> list) {
        List<Menu> topList = new ArrayList<>();
        for (Menu item : list) {
            if (item.getParentId() == 0) {
                List<Menu> childList = this.getChild(list, item.getId());
                for (Menu child : childList) {
                    if (!"".equals(child.getUrl())) {
                        item.setUrl(child.getUrl());
                        topList.add(item);
                        break;
                    }
                }
            }
        }
        return topList;
    }

    @Override
    public List<Map<String, Object>> getLeftList(List<Menu> list, List<Menu> activeMenuList) {
        Map<Long, Map<String, Object>> leftList = new LinkedHashMap<>();
        Menu currentTop = null;
        for (Menu item : list) {
            if (item.getParentId() == 0) {
                if (activeMenuList.contains(item)) {
                    currentTop = item;
                    break;
                }
            }
        }
        for (Menu item : list) {
            if (currentTop != null && item.getParentId().equals(currentTop.getId())) {
                Map<String, Object> map = new HashMap<>();
                map.put("item", item);
                leftList.put(item.getId(), map);
            }
        }
        List<Menu> childList;
        for (Menu item : list) {
            if (leftList.containsKey(item.getParentId())) {
                if (leftList.get(item.getParentId()).containsKey("list")) {
                    childList = (List<Menu>) leftList.get(item.getParentId()).get("list");
                } else {
                    childList = new ArrayList<>();
                }
                childList.add(item);
                leftList.get(item.getParentId()).put("list", childList);
            }
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Long, Map<String, Object>> entry : leftList.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    private List<Menu> getChild(List<Menu> list, long id) {
        List<Menu> childList = new ArrayList<>();
        for (Menu item : list) {
            if (item.getParentId() == id) {
                childList.add(item);
                childList.addAll(this.getChild(list, item.getId()));
            }
        }
        return childList;
    }

    @Override
    public List<Menu> getActiveList(List<Menu> list, String url) {
        List<Menu> menuList = new ArrayList<>();
        if (ObjectUtils.isEmpty(Util.trim(url, "/"))) {
            url = "/erp/siteInfo/baseInfo";
        }
        for (Menu item : list) {
            if (item.getUrl().equals(url)) {
                menuList.add(item);
                menuList.addAll(this.getParent(list, item.getParentId()));
                break;
            }
        }
        Collections.reverse(menuList);
        return menuList;
    }

    private List<Menu> getParent(List<Menu> list, long parentId) {
        List<Menu> menuList = new ArrayList<>();
        for (Menu item : list) {
            if (item.getId() == parentId) {
                menuList.add(item);
                menuList.addAll(this.getParent(list, item.getParentId()));
            }
        }
        return menuList;
    }

    @Override
    public boolean checkUserAuth(Admin admin, Menu menu) {
        Map<String, Object> params = new HashMap<>();
        params.put("adminId", admin.getAdminId());
        params.put("menuId", menu.getId());
        int count = menuMapper.checkUserAuth(params);
        if (count == 0) {
            return false;
        }
        return true;
    }

    public List<Dict> getTreeList() {
        List<Menu> menuList = menuMapper.selectList(null);
        return this.getChildren(menuList, 0);
    }

    public List<Dict> getChildren(List<Menu> menuList, long parentId) {
        List<Dict> result = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getParentId() == parentId) {
                Dict dict = new Dict();
                dict.setKey(String.valueOf(menu.getId()));
                dict.setTitle(menu.getName());
                dict.setValue(String.valueOf(menu.getId()));
                dict.setChildren(this.getChildren(menuList, menu.getId()));
                result.add(dict);
            }
        }
        return result;
    }

    public List<String> getAllMethodList(long id) {
        Map<String, Object> search = new HashMap<>();
        search.put("condition", "url!=''");
        List<Menu> menuList = menuMapper.selectList(search);
        List<String> existList = menuList.stream().map(Menu::getUrl).collect(Collectors.toList());
        List<String> urlList = new ArrayList<>();

        String currentUrl = "";
        if (id != 0) {
            search = new HashMap<>();
            search.put("id", id);
            Menu current = menuMapper.selectOne(search);
            currentUrl = current.getUrl();
        }
        Map<String, Object> beanList = applicationContext.getBeansWithAnnotation(RestController.class);
        //获取url与类和方法的对应信息
        for (Map.Entry<String, Object> bean : beanList.entrySet()) {
            Method[] methods = bean.getValue().getClass().getMethods();
            for (Method method : methods) {
                //findAnnotation也可以拿到其父类是否包含该注解
                if (null != method && null != AnnotationUtils.findAnnotation(method, Authorize.class)) {
                    String url = method.getAnnotation(Authorize.class).value();
                    if ((!existList.contains(url) || currentUrl.equals(url)) && !urlList.contains(url)) {
                        urlList.add(url);
                    }
                }
            }
        }
        urlList = urlList.stream().distinct().collect(Collectors.toList());
        return urlList;
    }

    public void saveMenu(Map<String, Object> params) throws Exception {
        this.save(params);
    }
}
