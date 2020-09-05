package org.eg.confessionwall.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (Post)实体类
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-25 11:52:44
 */
@ToString
public class Post implements Serializable {
    private static final long serialVersionUID = -29317690046775787L;
    /**
    * 帖子id
    */
    @Setter
    @Getter
    private Integer postId;
    /**
    * 用户id
    */
    @Setter
    @Getter
    private User user;
    /**
    * 帖子类型
    * 0:匿名表白; 1:公开表白; 2:分享; 3:吐槽
    */
    @Setter
    @Getter
    private Byte postType;
    /**
    * 帖子摘要
    */
    @Setter
    @Getter
    private String postAbstract;
    /**
    * 帖子内容
    */
    @Setter
    @Getter
    private String postContent;
    /**
    * 帖子访问数
    */
    @Setter
    @Getter
    private Integer postVisitedCount;
    /**
    * 回复数
    */
    @Setter
    @Getter
    private Integer answerCount;
    /**
    * 帖子被赞次数
    */
    @Setter
    @Getter
    private Integer postLikedCount;
    /**
    * 帖子状态
    * 1:正常 2:封禁 3:删除
    */
    @Setter
    @Getter
    private Byte postState;
    /**
    * 帖子创建时间
    */
    @Setter
    @Getter
    @JSONField(format="yyyy-MM-dd")
    private Date postCreateTime;
}