package com.shimh.aurora.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: shimh
 * @create: 2019年07月
 **/
@Slf4j
@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
public class AuroraMonitorApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AuroraMonitorApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        log.info("监控中心模块 启动成功......");
    }
}
