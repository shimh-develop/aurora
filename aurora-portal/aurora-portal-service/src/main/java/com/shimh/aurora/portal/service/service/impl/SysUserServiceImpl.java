package com.shimh.aurora.portal.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shimh.aurora.portal.service.entity.SysUser;
import com.shimh.aurora.portal.service.mapper.SysUserMapper;
import com.shimh.aurora.portal.service.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SysUserMapper userMapper;

    @Override
    @Transactional
    public void add(SysUser user) {
        save(user);
    }

    @Override
    public SysUser getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }
}
