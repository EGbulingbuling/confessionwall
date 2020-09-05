package org.eg.confessionwall.service;

import org.eg.confessionwall.entity.PostLike;
import org.eg.confessionwall.entity.User;

import java.util.List;

/**
 * (PostLike)表服务接口
 *
 * @author makejava
 * @since 2020-04-20 13:16:05
 */
public interface PostLikeService {

    /**
     * 通过ID查询单条数据
     *
     * @param likeId 主键
     * @return 实例对象
     */
    PostLike queryById(Integer likeId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<PostLike> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param postLike 实例对象
     * @return 实例对象
     */
    PostLike insert(PostLike postLike);

    /**
     * 修改数据
     *
     * @param postLike 实例对象
     * @return 实例对象
     */
    PostLike update(PostLike postLike);

    /**
     * 通过帖子用户查询是否点赞
     *
     * @param postId 帖子id
     * @param userId 用户id
     * @return 是否点赞
     */
    boolean isLike(Integer postId, Integer userId);

    /**
     *
     * @param postId 帖子id
     * @param userId 用户id
     * @return
     */
    boolean like(Integer postId, Integer userId);

    /**
     *
     * @param postId 帖子id
     * @param userId 用户id
     * @return
     */
    boolean undo(Integer postId, Integer userId);

    /**
     * 查询用户被赞次数
     * @param user
     * @return
     */
    int queryLikedNum(User user);
}