package org.eg.confessionwall.service;

import org.eg.confessionwall.entity.Post;
import org.eg.confessionwall.entity.Treasure;
import org.eg.confessionwall.entity.User;

import java.util.List;

/**
 * (Treasure)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 18:41:51
 */
public interface TreasureService {

    /**
     * 通过ID查询单条数据
     *
     * @param treasureId 主键
     * @return 实例对象
     */
    Treasure queryById(Integer treasureId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Treasure> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param treasure 实例对象
     * @return 对象列表
     */
    List<Treasure> queryAll(Treasure treasure);

    /**
     * 新增数据
     *
     * @param treasure 实例对象
     * @return 实例对象
     */
    Treasure insert(Treasure treasure);

    /**
     * 修改数据
     *
     * @param treasure 实例对象
     * @return 实例对象
     */
    Treasure update(Treasure treasure);

    /**
     *
     * @param postId 帖子id
     * @param user 当前用户
     * @return
     */
    boolean add(int postId, User user);

    /**
     *
     * @param postId 帖子id
     * @param user 当前用户
     * @return
     */
    boolean undo(int postId, User user);

    /**
     * 查询用户被收藏次数
     * @param user
     * @return
     */
    int queryTreasuredNum(User user);

    /**
     * 判断是否收藏
     * @param post
     * @param user
     * @return
     */
    boolean isTreasure(Post post, User user);
}