package org.eg.confessionwall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.eg.confessionwall.entity.Follow;
import org.apache.ibatis.annotations.Param;
import org.eg.confessionwall.entity.User;

import java.util.List;

/**
 * (Follow)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-24 16:40:09
 */
@Mapper
public interface FollowDao {

    /**
     * 通过ID查询单条数据
     *
     * @param followId 主键
     * @return 实例对象
     */
    Follow queryById(Integer followId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Follow> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param follow 实例对象
     * @return 对象列表
     */
    List<Follow> queryAll(@Param("follow")Follow follow);

    /**
     * 查询用户所有关注的人
     *
     * @param userId
     * @return 关注的人集合
     */
    List<Integer> queryAllFollowing(Integer userId);

//    /**
//     * 查询用户所有粉丝
//     *
//     * @param user
//     * @return 粉丝集合
//     */
//    List<Integer> queryAllFollower(@Param("user") User user);

    /**
     * 新增数据
     *
     * @param follow 实例对象
     * @return 影响行数
     */
    int insert(Follow follow);

    /**
     * 修改数据
     *
     * @param follow 实例对象
     * @return 影响行数
     */
    int update(Follow follow);

    /**
     *
     * @param followerId 关注者id
     * @param followedId 被关注者id
     * @return 影响行数
     */
    int deleteById(@Param("followerId")Integer followerId,@Param("followedId")Integer followedId);

    /**
     * 查询用户的粉丝数量
     * @param followedId
     * @return
     */
    int queryFollowedNum(Integer followedId);
}