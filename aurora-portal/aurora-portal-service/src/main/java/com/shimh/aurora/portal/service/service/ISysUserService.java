package com.shimh.aurora.portal.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shimh.aurora.portal.service.entity.SysUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shimh
 * @since 2019-08-30
 */
public interface ISysUserService extends IService<SysUser> {

    void add(SysUser user);

    SysUser getUserByUserName(String userName);
}
