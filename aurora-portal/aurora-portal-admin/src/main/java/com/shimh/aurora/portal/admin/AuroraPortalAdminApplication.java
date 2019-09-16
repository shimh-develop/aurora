package com.shimh.aurora.portal.admin;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.shimh.aurora.passport.EnablePassport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * @author: shimh
 * @create: 2019年08月
 **/
@Slf4j
@EnablePassport
@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
public class AuroraPortalAdminApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AuroraPortalAdminApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        log.info("集中权限管理平台 启动成功......");
    }

}
