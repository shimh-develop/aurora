package com.shimh.aurora.passport.filter;

import com.alibaba.fastjson.JSON;
import com.shimh.aurora.common.constant.ResultCode;
import com.shimh.aurora.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@Slf4j
public class LoginFilter extends AuthenticationFilter {

    private static final String DEFAULT_USERNAME_PARAM = "username";
    private static final String DEFAULT_PASSWORD_PARAM = "password";


    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        // !POST || 已经认证过的 放行
        if(!isPostRequest(request) || (null != getSubject(request, response) && getSubject(request, response).isAuthenticated())) {
            return true;
        }

        return false;
    }

    private boolean isPostRequest(ServletRequest request) {
        return WebUtils.toHttp(request).getMethod().toUpperCase(Locale.ENGLISH).equals(POST_METHOD);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        AuthenticationToken token = createToken(request, response);
        R r = new R();
        try {
            Subject subject = getSubject(request, response);
            // 认证
            subject.login(token);
            // 重定向 需判断 ajax
            if (isAjaxRequest(request, response)) {
                r.setResultCode(ResultCode.REDIRECT);
                r.setData(getSuccessUrl());
            } else {
                issueSuccessRedirect(request, response);
            }

        } catch (UnknownAccountException e) {
            log.error(e.getMessage(),e);
            r.setResultCode(ResultCode.USER_NOT_EXIST);
        }catch (AuthenticationException e) {
            log.error(e.getMessage(),e);
            r.setResultCode(ResultCode.USER_LOGIN_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            r.setResultCode(ResultCode.ERROR);
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        JSON.writeJSONString(response.getWriter(), r);

        return false;

    }

    private boolean isAjaxRequest(ServletRequest request, ServletResponse response) {
        return "XMLHttpRequest".equals(WebUtils.toHttp(request).getHeader("X-Requested-With"));
    }

    /**
     * 基于 username and password 生成Token
     *
     * @param request
     * @param response
     * @return
     */
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = request.getParameter(DEFAULT_USERNAME_PARAM);
        String password= request.getParameter(DEFAULT_PASSWORD_PARAM);
        return new UsernamePasswordToken(username, password);
    }


}
