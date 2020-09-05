package org.eg.confessionwall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.eg.confessionwall.entity.PostLike;
import org.apache.ibatis.annotations.Param;
import org.eg.confessionwall.entity.User;

import java.util.List;

/**
 * (PostLike)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-20 14:24:46
 */
@Mapper
public interface PostLikeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param likeId 主键
     * @return 实例对象
     */
    PostLike queryById(Integer likeId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<PostLike> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param postLike 实例对象
     * @return 对象列表
     */
    List<PostLike> queryAll(PostLike postLike);

    /**
     * 新增数据
     *
     * @param postLike 实例对象
     * @return 影响行数
     */
    int insert(PostLike postLike);

    /**
     * 修改数据
     *
     * @param postLike 实例对象
     * @return 影响行数
     */
    int update(PostLike postLike);

    /**
     * 通过主键删除数据
     *
     * @param postId
     * @param userId
     * @return 影响行数
     */
    int deleteByPidUid(Integer postId, Integer userId);

    /**
     * 通过外键删除数据
     *
     * @param postId
     * @return 影响行数
     */
    void deleteByPid(int postId);
}