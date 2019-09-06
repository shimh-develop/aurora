package com.shimh.aurora.portal.admin.service;

import com.shimh.aurora.portal.admin.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
