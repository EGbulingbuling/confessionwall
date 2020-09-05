package org.eg.confessionwall.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-03-24 16:33:35
 */
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = 648780603149640961L;
    /**
    * 用户id
    */
    @Setter
    @Getter
    private Integer userId;
    /**
    * 账号
    */
    @Setter
    @Getter
    private String account;
    /**
    * 密码
    */
    @Setter
    @Getter
    private String password;
    /**
     * 盐
     */
    @Setter
    @Getter
    private String salt;
    /**
    * 昵称
    */
    @Setter
    @Getter
    private String nickname;
    /**
    * 签名
    */
    @Setter
    @Getter
    private String autograph;
    /**
    * 性别
    * 男:0;女:1
    */
    @Setter
    @Getter
    private Byte sex;
    /**
    * 头像
    */
    @Setter
    @Getter
    private String head;
    /**
    * 经验
    */
    @Setter
    @Getter
    private Integer experience;
    /**
    * 连续签到次数
    */
    @Setter
    @Getter
    private Integer signedCount;
    /**
    * 手机号
    */
    @Setter
    @Getter
    private String phone;
    /**
    * 用户状态
    * 1：未激活 2：正常 3：封禁
    */
    @Setter
    @Getter
    private Byte userState;
    /**
    * 被封号次数
    */
    @Setter
    @Getter
    private Byte blockedCount;
    /**
    * 用户创建时间
    */
    @Setter
    @Getter
    private Date userCreatedTime;
    /**
    * 上一次登录时间
    */
    @Setter
    @Getter
    private Date lastLoginTime;
}