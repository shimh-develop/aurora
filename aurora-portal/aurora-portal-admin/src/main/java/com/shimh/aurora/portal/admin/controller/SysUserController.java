package com.shimh.aurora.portal.admin.controller;


import com.shimh.aurora.portal.service.api.UserApi;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shimh
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @GetMapping
    public String index() {
        return "success";
    }

    @RequiresRoles("admin")
    @GetMapping("/admin")
    public Object admin() {
        return SecurityUtils.getSubject().getPrincipal();
    }

    @RequiresPermissions("user:add")
    @GetMapping("/userAdd")
    public Object userAdd() {
        return "userAdd";
    }

    @RequiresPermissions("user:delete")
    @GetMapping("/userDelete")
    public Object userDelete() {
        return "userDelete";
    }

    @Autowired
    private UserApi userApi;

    @GetMapping("/test500")
    public String test500() {
       return userApi.test500();
    }

    @GetMapping("/testage")
    public String testage() {
        return userApi.age(444);
    }

    @GetMapping("/test404")
    public String test404() {
        return userApi.test404();
    }

}
