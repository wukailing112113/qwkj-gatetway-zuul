package com.wjsy.wjsygatetwayzuul.remote.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserAccessDto {

    public UserAccessDto() {

    }

    private List<String> permsSet;

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;
    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    //private Date expireTime;
    /**
     * 更新时间
     */
    //private Date updateTime;

    /**
     * 用户名
     */
    private String userName;


}
