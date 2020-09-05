package org.eg.confessionwall.service.impl;

import org.eg.confessionwall.dao.PostDao;
import org.eg.confessionwall.entity.Message;
import org.eg.confessionwall.dao.MessageDao;
import org.eg.confessionwall.entity.Post;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.MessageService;
import org.eg.confessionwall.utils.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息，多对多(Message)表服务实现类
 *
 * @author makejava
 * @since 2020-03-26 15:53:03
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageDao messageDao;
    @Resource
    private PostDao postDao;

    /**
     * 通过ID查询单条数据
     *
     * @param messageId 主键
     * @return 实例对象
     */
    @Override
    public Message queryById(Integer messageId) {
        return this.messageDao.queryById(messageId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Message> queryAllByLimit(int offset, int limit) {
        return this.messageDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询用户消息
     * @param pageUtil 分页工具
     * @param user 用户
     * @return 对象列表
     */
    @Override
    public List<Message> queryAll(PageUtil pageUtil, User user) {
        return this.messageDao.queryAllByUid(pageUtil,user.getUserId());
    }

    /**
     * 查询收到的消息总数
     * @param user
     * @return
     */
    @Override
    public int queryTotal(User user) {
        return this.messageDao.queryTotal(user.getUserId());
    }

    /**
     * 新增数据
     *
     * @param message 实例对象
     * @return 实例对象
     */
    @Override
    public Message insert(Message message) {
        this.messageDao.insert(message);
        return message;
    }

    /**
     * 修改数据
     *
     * @param message 实例对象
     * @return 实例对象
     */
    @Override
    public Message update(Message message) {
        this.messageDao.update(message);
        return this.queryById(message.getMessageId());
    }

    /**
     * 通过主键删除数据
     *
     * @param messageId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer messageId) {
        return this.messageDao.deleteById(messageId) > 0;
    }

    /**
     *
     * @param senderId 发送者id
     * @param receiverId 接收者id
     * @param messageContent 信息内容
     * @return 是否成功
     */
    @Override
    public boolean send(Integer senderId, Integer receiverId, String messageContent) {
        Message message=new Message();
        User sender=new User();
        User receiver=new User();
        sender.setUserId(senderId);
        receiver.setUserId(receiverId);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessageContent(messageContent);
        return messageDao.insert(message)>0;
    }

    /**
     * 将所有消息状态置为已读
     * @param user
     * @return
     */
    @Override
    public boolean read(User user) {
        Message message=new Message();
        message.setReceiver(user);
        message.setMessageState(2);
        return messageDao.updateForReceiver(message)>0;
    }

    /**
     * 发送系统消息
     * @param uid 用户id
     * @param pid 帖子id
     * @param type 系统消息类型 1:封贴提醒 2:关注提醒
     */
    @Override
    public void sendSysMsg(int uid, int pid, int type) {
        switch(type){
            case 1:
                Post post=postDao.queryById(pid);
                Message message=new Message();
                User receiver=new User();
                receiver.setUserId(uid);
                message.setReceiver(receiver);
                message.setMessageContent("<p>您的帖子 “<a>"+post.getPostAbstract()+"</a>” 被用户举报，已被封禁！如果封禁不合理，请点击 <a>这里</a> 进行申诉</p>");
                messageDao.insert(message);
                break;
//            case 2:
//                Message message1=new Message();
//                User receiver1=new User();
//                receiver1.setUserId(uid);
//                message1.setReceiver(receiver1);
//                message1.setMessageContent("");
        }
    }
}