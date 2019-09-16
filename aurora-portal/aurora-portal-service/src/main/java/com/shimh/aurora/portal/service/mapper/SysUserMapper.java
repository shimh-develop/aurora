package com.shimh.aurora.portal.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shimh.aurora.portal.service.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shimh
 * @since 2019-08-30
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUserByUserName(@Param("userName") String userName);
}
