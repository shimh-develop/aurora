package com.shimh.aurora.portal.service.controller;


import com.shimh.aurora.portal.service.api.UserApi;
import com.shimh.aurora.portal.service.api.vo.UserVo;
import com.shimh.aurora.portal.service.entity.SysUser;
import com.shimh.aurora.portal.service.exception.NotFoundException;
import com.shimh.aurora.portal.service.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
public class UserController {

    @Autowired
    private ISysUserService userService;

    @GetMapping("/getUserByUserName")
    public UserVo getUserByUserName(String userName) {
        UserVo userVo = new UserVo();
        SysUser user = userService.getUserByUserName(userName);
        BeanUtils.copyProperties(user, userVo);

        Set<String> roles = new HashSet<>();
        roles.add("admin");

        Set<String> permissions = new HashSet<>();
        permissions.add("user:add");
        permissions.add("user:update");

        userVo.setRoles(roles);
        userVo.setPermissions(permissions);
        return userVo;
    }


    @GetMapping("/500")
    public String test500() {
        int i = 1/0;
        return "500";
    }

    @GetMapping("/age")
    public String age(int age) {
        return "cccc";
    }

    @GetMapping("/404")
    public String test404() {
        throw new NotFoundException("404 没有找到");
    }

}
