package org.eg.confessionwall.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * (SignIn)实体类
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-04-11 21:37:21
 */
public class SignIn implements Serializable {
    private static final long serialVersionUID = 896500310082744622L;
    /**
    * 签到id
    */
    @Setter
    @Getter
    private Integer signId;
    /**
    * 签到者id
    */
    @Setter
    @Getter
    private Integer userId;
    /**
    * 签到时间
    */
    @Setter
    @Getter
    private Date signTime;
}