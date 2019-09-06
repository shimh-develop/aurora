package com.shimh.aurora.portal.admin.service.impl;

import com.shimh.aurora.portal.admin.entity.SysUser;
import com.shimh.aurora.portal.admin.mapper.SysUserMapper;
import com.shimh.aurora.portal.admin.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shimh
 * @since 2019-08-30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    @Transactional
    public void add(SysUser user) {
        save(user);
        int a = 1/0;
    }
}
