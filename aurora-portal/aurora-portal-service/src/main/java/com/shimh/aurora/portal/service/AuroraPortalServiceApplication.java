package com.shimh.aurora.portal.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: shimh
 * @create: 2019年08月
 **/
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class AuroraPortalServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AuroraPortalServiceApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        log.info("用户、角色、权限微服务 启动成功......");
    }

}
