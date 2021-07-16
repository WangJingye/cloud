package com.delcache.website;

import com.delcache.website.common.annotation.EnableCustomConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableCustomConfig
@SpringBootApplication
@EnableDiscoveryClient
public class WebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsiteApplication.class, args);
        System.out.println("Website Success");
    }

}
