package com.shimh.aurora.portal.admin.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
        // System.out.println(SecurityUtils.getSubject().getPrincipal());
        return "success";
    }

    @RequiresRoles("shimh")
    @GetMapping("/role")
    public Object role() {
        // System.out.println(SecurityUtils.getSubject().getPrincipal());
        return SecurityUtils.getSubject().getPrincipal();
    }

}
