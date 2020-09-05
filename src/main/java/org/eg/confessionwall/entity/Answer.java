package org.eg.confessionwall.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * 回复表(Answer)实体类
 *
 * @author makejava
 * @since 2020-03-24 16:41:18
 */
public class Answer implements Serializable {
    private static final long serialVersionUID = 582474508558679474L;
    /**
    * 回复id
    */
    @Setter
    @Getter
    private Integer answerId;
    /**
    * 被回复者
    */
    @Setter
    @Getter
    private User respondent;
    /**
    * 帖子id
    */
    @Setter
    @Getter
    private Post post;
    /**
    * 回复者
    */
    @Setter
    @Getter
    private User user;
    /**
    * 回复内容
    */
    @Setter
    @Getter
    private String answerContent;
    /**
    * 回复被赞次数
    * 该属性废弃
    */
    @Setter
    @Getter
    private Integer answerLikedCount;
    /**
    * 回复创建时间
    */
    @Setter
    @Getter
    private Date answerCreateTime;
}