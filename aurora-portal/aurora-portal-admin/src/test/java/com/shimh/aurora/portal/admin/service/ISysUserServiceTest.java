package com.shimh.aurora.portal.admin.service;

import com.shimh.aurora.portal.admin.AuroraPortalAdminApplicationTest;
import com.shimh.aurora.portal.admin.entity.SysUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author: shimh
 * @create: 2019年08月
 **/
public class ISysUserServiceTest extends AuroraPortalAdminApplicationTest {

    @Autowired
    private ISysUserService userService;

    @Test
    public void test1() {
        SysUser user = SysUser.builder()
                .userName("shimh1")
                .nickName("shimh")
                .password("123456")
                .phone("123")
                .email("1@1.com")
                .createTime(LocalDateTime.now())
                .build();

        userService.add(user);
    }
    @Test
    public void get() {
        System.out.println(userService.getById(1));
    }
    @Test
    public void delete() {
        userService.removeById(1);
    }

}
