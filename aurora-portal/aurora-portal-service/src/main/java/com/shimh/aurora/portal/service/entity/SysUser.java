package com.shimh.aurora.portal.service.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * <p>
 * 
 * </p>
 *
 * @author shimh
 * @since 2019-08-30
 */
@Data
@Builder
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Tolerate
    public SysUser() {
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String nickName;

    private String password;

    /**
     * 0 正常 1 删除
     */
    private Integer delFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String phone;

    private String email;

    private String slat;

    private String disabled;


}
