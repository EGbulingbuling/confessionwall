package org.eg.confessionwall.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * (Activate)实体类
 *
 * @author makejava
 * @since 2020-05-12 19:08:09
 */
public class Activate implements Serializable {
    private static final long serialVersionUID = 861733651837858136L;
    /**
    * 激活id
    */
    @Setter
    @Getter
    private Integer activateId;
    /**
    * 激活者id
    */
    @Setter
    @Getter
    private Integer userId;
    /**
    * 激活码
    */
    @Setter
    @Getter
    private String activationCode;
    /**
    * 激活状态
    * 1：未激活 2：已激活
    */
    @Setter
    @Getter
    private Byte activateState;
    /**
    * 激活建立时间
    */
    @Setter
    @Getter
    private Date activateTime;
}