package com.delcache.website.common.filter;

import com.delcache.website.common.config.properties.IgnoreWhiteProperties;
import com.delcache.website.common.domain.Admin;
import com.delcache.website.common.domain.Menu;
import com.delcache.website.common.exception.AuthException;
import com.delcache.website.common.exception.LoginException;
import com.delcache.website.system.service.IAdminService;
import com.delcache.website.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 网关鉴权
 */
@Component
public class AuthFilter implements HandlerInterceptor {

    @Autowired
    IAdminService adminService;
    @Autowired
    IMenuService menuService;
    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //  后台session控制
        return AuthFilter(request, response, o);
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, Exception e) throws Exception {

    }

    private Boolean AuthFilter(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        String token = request.getHeader("token");
        //不需要登录
        if (ignoreWhite.getWhites().contains(uri)) {
            return true;
        }
        if (ObjectUtils.isEmpty(token)) {
            throw new LoginException("令牌不能为空，请重新的登录");
        }
        Map<String, Object> search = new HashMap<>();
        search.put("token", token);
        Admin user = adminService.find(search);
        if (user == null) {
            throw new LoginException("令牌已失效，请重新的登录");
        }
        if (user.getLastLoginTime() + 24 * 3600 < new Date().getTime() / 1000) {
            throw new LoginException("令牌已失效，请重新的登录");
        }
        //用户被禁用
        if (user.getStatus() == 0) {
            throw new LoginException("用户已禁用，请联系管理员");
        }
        request.setAttribute("user", user);
        //不需要权限
        if (ignoreWhite.getNoAuthList().contains(uri)) {
            return true;
        }
        search = new HashMap<>();
        search.put("url", uri);
        Menu currentMenu = menuService.find(search);
        //菜单不存在就不添加了
        if (currentMenu == null) {
            return true;
        }
        if (user.getIdentity() == 0 && !menuService.checkUserAuth(user, currentMenu)) {
            throw new AuthException("您暂无该权限");
        }
        return true;
    }

    /**
     * @param request
     * @return Create Date:2013-6-5
     * @author Shine
     * Description:获取IP
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}