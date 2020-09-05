package org.eg.confessionwall.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * (Notice)实体类
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-24 16:39:13
 */
public class Notice implements Serializable {
    private static final long serialVersionUID = 434352327541328409L;
    /**
    * 公告id
    */
    @Setter
    @Getter
    private Integer noticeId;
    /**
    * 公告标题
    */
    @Setter
    @Getter
    private String title;
    /**
    * 公告内容
    */
    @Setter
    @Getter
    private String noticeContent;
    /**
    * 公告访问数
    */
    @Setter
    @Getter
    private Integer noticeVisitedCount;
    /**
    * 公告创建时间
    */
    @Setter
    @Getter
    private Date noticeCreateTime;
}