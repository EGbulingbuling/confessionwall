package org.eg.confessionwall.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * (PostLike)实体类
 *
 * @author makejava
 * @since 2020-04-20 14:24:43
 */
public class PostLike implements Serializable {
    private static final long serialVersionUID = -38582346339336765L;
    /**
    * 赞id
    */
    @Setter
    @Getter
    private Integer likeId;
    /**
    * 点赞者id
    */
    @Setter
    @Getter
    private Integer userId;
    /**
    * 帖子id
    */
    @Setter
    @Getter
    private Integer postId;
    /**
    * 点赞时间
    */
    @Setter
    @Getter
    private Date likeTime;
}