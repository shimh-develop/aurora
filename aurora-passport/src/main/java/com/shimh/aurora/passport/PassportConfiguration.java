package com.shimh.aurora.passport;

import com.shimh.aurora.passport.filter.LoginFilter;
import com.shimh.aurora.passport.realm.AuthRealm;
import com.shimh.aurora.passport.session.RedisSessionDAO;
import com.shimh.aurora.portal.service.api.UserApi;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
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
@EnableConfigurationProperties({PassportProperties.class})
@Configuration
public class PassportConfiguration {

    @Autowired
    private PassportProperties passportProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public WebSecurityManager securityManager(AuthRealm authRealm, DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(authRealm);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        return defaultWebSecurityManager;
    }


    @Bean
    public AuthRealm authRealm(UserApi userApi,HashedCredentialsMatcher hashedCredentialsMatcher) {
        AuthRealm realm = new AuthRealm(userApi);
        realm.setCredentialsMatcher(hashedCredentialsMatcher);
        //暂时不使用缓存
        realm.setCachingEnabled(false);
        realm.setAuthenticationCachingEnabled(false);
        realm.setAuthorizationCachingEnabled(false);
        return realm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(SimpleCookie simpleCookie, SessionDAO sessionDAO){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookie(simpleCookie);
        sessionManager.setGlobalSessionTimeout(passportProperties.getSessionTimeout());
        sessionManager.setSessionIdUrlRewritingEnabled(false); // 解决重定向时【 url出现;JSESSIONID= 】问题
        sessionManager.setDeleteInvalidSessions(true);
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
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(passportProperties.getLoginUrl());
        shiroFilterFactoryBean.setSuccessUrl(passportProperties.getSuccessUrl());
        shiroFilterFactoryBean.setUnauthorizedUrl(passportProperties.getUnauthorizedUrl());

        loadShiroFilterChain(shiroFilterFactoryBean);

        Map<String, Filter> filters = new HashMap<>(1);
        //登录filter
        LoginFilter loginFilter = new LoginFilter();
        filters.put("login", loginFilter);

        shiroFilterFactoryBean.setFilters(filters);

        return shiroFilterFactoryBean;
    }

    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean){

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 默认配置
        // 登录请求 不拦截
        filterChainDefinitionMap.put(passportProperties.getLoginUrl(), "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        // 执行登录操作 POST 拦截
        filterChainDefinitionMap.put("/login", "login");
        filterChainDefinitionMap.put(passportProperties.getLogoutUrl(), "logout");

        //配置文件中的规则
        if(passportProperties.getFilterChainDefinition() != null && passportProperties.getFilterChainDefinition().keySet().size() > 0){
            filterChainDefinitionMap.putAll(passportProperties.getFilterChainDefinition());
        }

        if (null == filterChainDefinitionMap.get("/**") || "".equals(filterChainDefinitionMap.get("/**"))) {
            filterChainDefinitionMap.put("/**", "authc");
        }

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
