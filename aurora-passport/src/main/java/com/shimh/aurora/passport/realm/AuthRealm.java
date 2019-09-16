package com.shimh.aurora.passport.realm;

import com.shimh.aurora.common.constant.Base;
import com.shimh.aurora.portal.service.api.UserApi;
import com.shimh.aurora.portal.service.api.vo.UserVo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
public class AuthRealm extends AuthorizingRealm {

    private UserApi userApi;

    public AuthRealm(UserApi userApi) {
        Objects.requireNonNull(userApi, "UserApi不能是空. ");
        this.userApi = userApi;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();

        UserVo userVo = userApi.getUserByUserName(userName);
        System.out.println("doGetAuthenticationInfo....");
        System.out.println(userVo);

        if (userVo == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if (Base.DISABLED.equals(userVo.getDisabled())) {
            throw new LockedAccountException(); //帐号锁定
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userVo,
                userVo.getPassword(),
                ByteSource.Util.bytes(userVo.getSlat()),
                getName()
        );
        return authenticationInfo;
    }

    /**
     * 授权/验权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserVo userVo = (UserVo) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        authorizationInfo.setRoles(userVo.getRoles());
        authorizationInfo.setStringPermissions(userVo.getPermissions());
        return authorizationInfo;
    }


    // 使用clientName + userName来作为缓存key
//    @Override
//    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
//        Pac4jPrincipal pac4jPrincipal = (Pac4jPrincipal) principals.getPrimaryPrincipal();
//        return pac4jPrincipal.getProfile().getId();
//    }

    // 使用clientName + userName来作为缓存key
//    @Override
//    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
//
//        return super.getAuthenticationCacheKey(token);
//    }

}
