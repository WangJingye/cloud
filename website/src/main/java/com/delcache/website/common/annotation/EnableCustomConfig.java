package com.delcache.website.common.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
// 指定要扫描的Mapper类的包的路径
@MapperScan("com.delcache.website.**.mapper")
// 开启线程异步执行
@EnableAsync
public @interface EnableCustomConfig
{

}
