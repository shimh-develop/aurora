package com.shimh.aurora.portal.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author: shimh
 * @create: 2019年08月
 **/
@Slf4j
@SpringBootApplication
// @EnableCasClient // 开启CAS支持
public class AuroraPortalAdminApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AuroraPortalAdminApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        log.info("集中权限管理平台 启动成功......");
    }

}
