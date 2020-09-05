package org.eg.confessionwall.service;

import org.eg.confessionwall.entity.Follow;
import org.eg.confessionwall.entity.User;

import java.util.List;

/**
 * (Follow)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 16:40:09
 */
public interface FollowService {

    /**
     * 通过ID查询单条数据
     *
     * @param followId 主键
     * @return 实例对象
     */
    Follow queryById(Integer followId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Follow> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param follow 实例对象
     * @return 实例对象
     */
    Follow insert(Follow follow);

    /**
     * 修改数据
     *
     * @param follow 实例对象
     * @return 实例对象
     */
    Follow update(Follow follow);

    /**
     *
     * @param follower 关注者
     * @param followed 被关注者
     * @return
     */
    boolean isFollow(Integer follower, Integer followed);

    /**
     *
     * @param uid 被关注者
     * @param user 关注者
     * @return
     */
    boolean add(int uid, User user);

    /**
     *
     * @param uid 被关注者
     * @param user 关注者
     * @return
     */
    boolean undo(int uid, User user);

    /**
     * 查询用户的粉丝数量
     * @param user
     * @return
     */
    int queryFollowedNum(User user);
}