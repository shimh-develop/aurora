package com.shimh.aurora.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author: shimh
 * @create: 2019年07月
 **/

@Slf4j
@EnableEurekaServer
@SpringBootApplication
public class AuroraEurekaApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AuroraEurekaApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        log.info("服务注册中心 启动成功......");
    }

}
