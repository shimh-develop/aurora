package com.shimh.aurora.zuul;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@Slf4j
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@EnableZuulProxy
@SpringBootApplication
public class AuroraZuulApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AuroraZuulApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        log.info("网关服务模块 启动成功......");
    }
}
