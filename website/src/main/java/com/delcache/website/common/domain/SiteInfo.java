package com.delcache.website.common.domain;


import com.delcache.website.common.annotation.PrimaryKey;

public class SiteInfo extends BaseEntity {
    @PrimaryKey
    private Long id;

    private String webName;

    private String webHost;

    private String webIp;

    private String defaultPassword;

    private String wechatAppId;

    private String wechatAppSecret;

    private String wechatMchId;

    private String wechatPayKey;

    private String wechatNotify;

    private String appLogo;

    private String aboutUs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getWebHost() {
        return webHost;
    }

    public void setWebHost(String webHost) {
        this.webHost = webHost;
    }

    public String getWebIp() {
        return webIp;
    }

    public void setWebIp(String webIp) {
        this.webIp = webIp;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getWechatAppId() {
        return wechatAppId;
    }

    public void setWechatAppId(String wechatAppId) {
        this.wechatAppId = wechatAppId;
    }

    public String getWechatAppSecret() {
        return wechatAppSecret;
    }

    public void setWechatAppSecret(String wechatAppSecret) {
        this.wechatAppSecret = wechatAppSecret;
    }

    public String getWechatMchId() {
        return wechatMchId;
    }

    public void setWechatMchId(String wechatMchId) {
        this.wechatMchId = wechatMchId;
    }

    public String getWechatPayKey() {
        return wechatPayKey;
    }

    public void setWechatPayKey(String wechatPayKey) {
        this.wechatPayKey = wechatPayKey;
    }

    public String getWechatNotify() {
        return wechatNotify;
    }

    public void setWechatNotify(String wechatNotify) {
        this.wechatNotify = wechatNotify;
    }

    public String getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }
}
 
    
    