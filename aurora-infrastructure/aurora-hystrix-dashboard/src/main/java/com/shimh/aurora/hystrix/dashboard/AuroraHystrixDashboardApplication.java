package com.shimh.aurora.hystrix.dashboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@Slf4j
@EnableHystrixDashboard
@EnableDiscoveryClient
@EnableTurbine
@SpringBootApplication
public class AuroraHystrixDashboardApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AuroraHystrixDashboardApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        log.info("Hystrix监控面板 启动成功......");
    }

}
