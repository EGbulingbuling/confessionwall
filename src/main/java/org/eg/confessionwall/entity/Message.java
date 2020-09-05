package org.eg.confessionwall.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * 消息，多对多(Message)实体类
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-26 15:53:02
 */
public class Message implements Serializable {
    private static final long serialVersionUID = -55191713806482278L;
    /**
    * 消息id
    */
    @Setter
    @Getter
    private Integer messageId;
    /**
    * 发送者
    */
    @Setter
    @Getter
    private User sender;
    /**
    * 接受者
    */
    @Setter
    @Getter
    private User receiver;
    /**
    * 消息内容
    */
    @Setter
    @Getter
    private String messageContent;
    /**
    * 消息状态
    * 1：未读 2：已读
    */
    @Setter
    @Getter
    private Object messageState;
    /**
    * 消息发送时间
    */
    @Setter
    @Getter
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date messageCreateTime;
}