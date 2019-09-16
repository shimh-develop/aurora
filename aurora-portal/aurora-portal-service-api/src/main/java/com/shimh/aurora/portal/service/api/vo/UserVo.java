package com.shimh.aurora.portal.service.api.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@Builder
@Data
public class UserVo {

    private static final long serialVersionUID = 1L;

    @Tolerate
    public UserVo() {
    }

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

    private Set<String> roles;

    private Set<String> permissions;

}
