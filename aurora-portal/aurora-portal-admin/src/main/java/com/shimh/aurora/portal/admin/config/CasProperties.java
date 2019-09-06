package com.shimh.aurora.portal.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@Data
@ConfigurationProperties(prefix = "cas")
public class CasProperties {

    /**
     * cas 服务端地址
     */
    private String serverUrlPrefix = "http://localhost:8080/cas";

    /**
     * 当前客户端地址
     */
    private String clientUrlPrefix = "";
    private String clientName;

    private String loginUrl = "/login";
    private String logoutUrl = "/logout";
    private String successUrl = "/";
    private String unauthorizedUrl = "/403";

    private long sessionTimeout = 30 * 60 * 1000;

}
