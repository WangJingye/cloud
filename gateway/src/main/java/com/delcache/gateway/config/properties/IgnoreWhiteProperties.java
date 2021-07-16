package com.delcache.gateway.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 放行白名单配置
 * 
 * @author ruoyi
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "ignore")
public class IgnoreWhiteProperties
{
    /**
     * 放行白名单配置，网关不校验此处的白名单
     */
    private List<String> gatewayIgnores = new ArrayList<>();

    public List<String> getGatewayIgnores() {
        return gatewayIgnores;
    }

    public void setGatewayIgnores(List<String> gatewayIgnores) {
        this.gatewayIgnores = gatewayIgnores;
    }
}
