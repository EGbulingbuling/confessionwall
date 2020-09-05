package org.eg.confessionwall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.eg.confessionwall.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.eg.confessionwall.utils.PageUtil;

import java.util.List;

/**
 * 消息，多对多(Message)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-26 15:53:02
 */
@Mapper
public interface MessageDao {

    /**
     * 通过ID查询单条数据
     *
     * @param messageId 主键
     * @return 实例对象
     */
    Message queryById(Integer messageId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Message> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param message 实例对象
     * @return 对象列表
     */
    List<Message> queryAll(@Param("message") Message message);

    /**
     * 新增数据
     *
     * @param message 实例对象
     * @return 影响行数
     */
    int insert(Message message);

    /**
     * 修改数据
     *
     * @param message 实例对象
     * @return 影响行数
     */
    int update(Message message);

    /**
     * 通过主键删除数据
     *
     * @param messageId 主键
     * @return 影响行数
     */
    int deleteById(Integer messageId);

    /**
     * 通过收信人修改数据
     *
     * @param message 实例对象
     * @return 影响行数
     */
    int updateForReceiver(Message message);

    /**
     * 查询用户收到的消息
     * @param pageUtil
     * @param userId
     * @return
     */
    List<Message> queryAllByUid(@Param("pageUtil") PageUtil pageUtil,@Param("userId") Integer userId);

    /**
     * 查询收到的消息总数
     * @param userId
     * @return
     */
    int queryTotal(@Param("userId") Integer userId);
}