package com.shimh.aurora.portal.admin.shiro;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import io.buji.pac4j.token.Pac4jToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.pac4j.cas.profile.CasProfile;
import org.pac4j.core.profile.CommonProfile;

import java.util.*;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
public class CasRealm extends Pac4jRealm {


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        final Pac4jToken pac4jToken = (Pac4jToken) authenticationToken;
        final List<CommonProfile> commonProfileList = pac4jToken.getProfiles();
        final CommonProfile commonProfile = commonProfileList.get(0);
        System.out.println("shiro认证开始：单点登录返回的信息");
        System.out.println(commonProfile.toString());
        System.out.println("shiro认证结束：单点登录返回的信息");
        final Pac4jPrincipal principal = new Pac4jPrincipal(commonProfileList, getPrincipalNameAttribute());

        String userName = principal.getProfile().getId();

        final PrincipalCollection principalCollection = new SimplePrincipalCollection(Arrays.asList(principal, userName), getName());

        return new SimpleAuthenticationInfo(principalCollection, commonProfileList.hashCode());
    }

    /**
     * 授权/验权（todo 后续有权限在此增加）
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = principals.oneByType(String.class);

        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        System.out.println("shiro鉴权开始：。。。");
        Set<String> roles = new HashSet<>();
        roles.add(username);
        System.out.println(roles);
        System.out.println("shiro鉴权结束：。。。");
        authInfo.setRoles(roles);
        return authInfo;
    }


    // 使用username来作为缓存key
    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        Pac4jPrincipal pac4jPrincipal = (Pac4jPrincipal) principals.getPrimaryPrincipal();
        return pac4jPrincipal.getProfile().getId();
    }

    // 使用username来作为缓存key
    @Override
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        if (token instanceof Pac4jToken) {
            Pac4jToken pac4jToken = (Pac4jToken) token;
            Object principal = pac4jToken.getPrincipal();
            if (principal instanceof Optional) {
                @SuppressWarnings("unchecked") Optional<CasProfile> casProfileOptional = (Optional<CasProfile>) principal;
                return casProfileOptional.get().getId();
            }
        }
        return super.getAuthenticationCacheKey(token);
    }

}
