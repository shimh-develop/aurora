package com.shimh.aurora.passport;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@Data
@ConfigurationProperties(prefix = "passport")
public class PassportProperties {

    private String clientName;

    private String loginUrl = "/login.html";
    private String logoutUrl = "/logout";
    private String successUrl = "/";
    private String unauthorizedUrl = "/403";
    private long sessionTimeout = 30 * 60 * 1000;

    private LinkedHashMap<String,String> filterChainDefinition = new LinkedHashMap<String,String>();

}
