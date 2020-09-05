package org.eg.confessionwall.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * 访客关系多对多(Visitor)实体类
 *
 * @author makejava
 * @since 2020-03-24 16:35:57
 */
public class Visitor implements Serializable {
    private static final long serialVersionUID = 750515218851616193L;
    /**
    * 访问id
    */
    @Setter
    @Getter
    private Integer visitId;
    /**
    * 访问者id
    */
    @Setter
    @Getter
    private User visitor;
    /**
    * 被访者id
    */
    @Setter
    @Getter
    private User visited;
    /**
    * 访问时间
    */
    @Setter
    @Getter
    private Date visitedTime;
}