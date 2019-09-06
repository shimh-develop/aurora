package com.shimh.aurora.portal.admin.config;

import io.buji.pac4j.context.ShiroSessionStore;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.logout.handler.DefaultLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@EnableConfigurationProperties({CasProperties.class})
@Configuration
public class Pac4jConfig {

    @Autowired
    private CasProperties casProperties;


    @Bean
    public Config config(Clients clients, ShiroSessionStore sessionStore) {
        Config config = new Config(clients);
        config.setSessionStore(sessionStore);
        return config;
    }

    @Bean
    ShiroSessionStore sessionStore(){
        return new ShiroSessionStore();
    }

    @Bean
    public Clients clients(CasClient casClient) {
        Clients clients = new Clients(casProperties.getClientUrlPrefix() + "/callback?client_name=" + casProperties.getClientName(), casClient);
        return clients;
    }

    @Bean
    public CasClient casClient(CasConfiguration casConfig){
        CasClient casClient = new CasClient(casConfig);
        casClient.setCallbackUrl(casProperties.getClientUrlPrefix() + "/callback?client_name=" + casProperties.getClientName());
        casClient.setName(casProperties.getClientName());
        return casClient;
    }

    @Bean
    public CasConfiguration casConfig(){
        final CasConfiguration configuration = new CasConfiguration();
        configuration.setLoginUrl(casProperties.getServerUrlPrefix() + casProperties.getLoginUrl());
        configuration.setAcceptAnyProxy(true);
        configuration.setPrefixUrl(casProperties.getServerUrlPrefix() + "/");
        return configuration;
    }



}
