package org.eg.confessionwall.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * (Follow)实体类
 *
 * @author makejava
 * @since 2020-03-24 16:40:09
 */
public class Follow implements Serializable {
    private static final long serialVersionUID = 414078918493479740L;
    /**
    * 关注id
    */
    @Setter
    @Getter
    private Integer followId;
    /**
    * 关注者
    */
    @Setter
    @Getter
    private User follower;
    /**
    * 被关注者
    */
    @Setter
    @Getter
    private User followed;
    /**
    * 关注时间
    */
    @Setter
    @Getter
    private Date followedTime;
}