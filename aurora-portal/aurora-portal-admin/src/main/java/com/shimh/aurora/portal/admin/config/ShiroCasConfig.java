package com.shimh.aurora.portal.admin.config;

import com.shimh.aurora.portal.admin.shiro.CasRealm;
import com.shimh.aurora.portal.admin.shiro.RedisSessionDAO;
import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.subject.Pac4jSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.pac4j.core.config.Config;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@EnableConfigurationProperties({CasProperties.class})
@Configuration
public class ShiroCasConfig {

    @Autowired
    private CasProperties casProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public WebSecurityManager securityManager(CasRealm casRealm, SubjectFactory subjectFactory, DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(casRealm);
        defaultWebSecurityManager.setSubjectFactory(subjectFactory);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        return defaultWebSecurityManager;
    }


    @Bean
    public CasRealm casRealm() {
        CasRealm realm = new CasRealm();
        realm.setCachingEnabled(false);
        //暂时不使用缓存
        realm.setAuthenticationCachingEnabled(false);
        realm.setAuthorizationCachingEnabled(false);
        return realm;
    }
    @Bean
    public Pac4jSubjectFactory subjectFactory(){
        return new Pac4jSubjectFactory();
    }


    @Bean
    public DefaultWebSessionManager sessionManager(SimpleCookie simpleCookie, SessionDAO sessionDAO){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookie(simpleCookie);
        sessionManager.setGlobalSessionTimeout(casProperties.getSessionTimeout());
        // sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    @Bean
    public SessionDAO sessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisTemplate(redisTemplate);
        return sessionDAO;
    }

    @Bean
    public SimpleCookie sessionIdCookie(){
        SimpleCookie cookie = new SimpleCookie("s_id");
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        return cookie;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return filterRegistration;
    }


    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(Config config, SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(casProperties.getServerUrlPrefix() + "?service=" + casProperties.getClientUrlPrefix() + "/callback?client_name=" + casProperties.getClientName());
        shiroFilterFactoryBean.setSuccessUrl(casProperties.getSuccessUrl());
        shiroFilterFactoryBean.setUnauthorizedUrl(casProperties.getUnauthorizedUrl());

        loadShiroFilterChain(shiroFilterFactoryBean);

        Map<String, Filter> filters = new HashMap<>(3);
        //cas 资源认证拦截器
        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setConfig(config);
        securityFilter.setClients(casProperties.getClientName());
        filters.put("securityFilter", securityFilter);

        //cas 认证后回调拦截器
        CallbackFilter callbackFilter = new CallbackFilter();
        callbackFilter.setConfig(config);
        callbackFilter.setDefaultUrl(casProperties.getClientUrlPrefix());
        filters.put("callbackFilter", callbackFilter);

        // 注销 拦截器
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setConfig(config);
        logoutFilter.setCentralLogout(true);
        logoutFilter.setLocalLogout(true);
        logoutFilter.setDefaultUrl(casProperties.getClientUrlPrefix() + "/callback?client_name=" + casProperties.getClientName());
        filters.put("logout",logoutFilter);

        shiroFilterFactoryBean.setFilters(filters);

        return shiroFilterFactoryBean;
    }

    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean){
        /*下面这些规则配置最好配置到配置文件中 */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/callback", "callbackFilter");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**","securityFilter,authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


}
