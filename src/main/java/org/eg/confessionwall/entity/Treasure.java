package org.eg.confessionwall.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * (Treasure)实体类
 *
 * @author makejava
 * @since 2020-03-24 18:41:51
 */
public class Treasure implements Serializable {
    private static final long serialVersionUID = 115589598986743279L;
    /**
    * 收藏id
    */
    @Setter
    @Getter
    private Integer treasureId;
    /**
    * 用户id
    */
    @Setter
    @Getter
    private User user;
    /**
    * 帖子id
    */
    @Setter
    @Getter
    private Post post;
    /**
    * 收藏时间
    */
    @Setter
    @Getter
    private Date treasureTime;
}