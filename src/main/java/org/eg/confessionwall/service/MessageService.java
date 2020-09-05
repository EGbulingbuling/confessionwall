package org.eg.confessionwall.service;

import org.eg.confessionwall.entity.Message;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.utils.PageUtil;

import java.util.List;

/**
 * 消息，多对多(Message)表服务接口
 *
 * @author makejava
 * @since 2020-03-26 15:53:02
 */
public interface MessageService {

    /**
     * 通过ID查询单条数据
     *
     * @param messageId 主键
     * @return 实例对象
     */
    Message queryById(Integer messageId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Message> queryAllByLimit(int offset, int limit);

    /**
     * 查询用户消息
     *
     * @param pageUtil 分页工具
     * @param user 用户
     * @return 对象列表
     */
    List<Message> queryAll(PageUtil pageUtil,User user);

    /**
     * 查询收到的消息总数
     * @param user
     * @return
     */
    int queryTotal(User user);

    /**
     * 新增数据
     *
     * @param message 实例对象
     * @return 实例对象
     */
    Message insert(Message message);

    /**
     * 修改数据
     *
     * @param message 实例对象
     * @return 实例对象
     */
    Message update(Message message);

    /**
     * 通过主键删除数据
     *
     * @param messageId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer messageId);

    /**
     *
     * @param senderId 发送者id
     * @param receiverId 接收者id
     * @param messageContent 信息内容
     * @return 是否成功
     */
    boolean send(Integer senderId, Integer receiverId, String messageContent);

    /**
     * 将所有消息状态置为已读
     * @param user
     * @return
     */
    boolean read(User user);

    /**
     * 发送系统消息
     * @param uid
     * @param pid
     * @param type
     */
    void sendSysMsg(int uid, int pid, int type);
}