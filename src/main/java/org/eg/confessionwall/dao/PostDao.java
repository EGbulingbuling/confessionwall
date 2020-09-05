package org.eg.confessionwall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.eg.confessionwall.entity.Post;
import org.apache.ibatis.annotations.Param;
import org.eg.confessionwall.utils.PageUtil;

import java.util.List;

/**
 * (Post)表数据库访问层
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-25 11:52:44
 */
@Mapper
public interface PostDao {

    /**
     * 通过ID查询单条数据
     *
     * @param postId 主键
     * @return 实例对象
     */
    Post queryById(Integer postId);


    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Post> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询本周热门
     * 查询一周内，评论数最高的前10条帖子
     *
     * @return 对象列表
     */
    List<Post> queryWeekHot();

    /**
     * 选出最近15天从起始数开始的页面单位数量的数据
     *
     * @param pageUtil 查询起始位置
     * @return 对象列表
     */
    List<Post> queryAllByTime(@Param("pageUtil") PageUtil pageUtil);

    /**
     * 查询最近15天的数据数量
     * @return 数据数量
     */
    int queryTotalByTime();

    /**
     * 查询关注者的所有帖子（非匿名）
     *
     * @param pageUtil 查询起始位置
     * @param following
     * @return 对象列表
     */
    List<Post> queryFollowingAll(@Param("pageUtil") PageUtil pageUtil,@Param("following") List<Integer> following);

    /**
     * 查询关注者的所有帖子总数（非匿名）
     * @return 数据数量
     * @param following
     */
    int queryFollowingTotal(List<Integer> following);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param post 实例对象
     * @return 对象列表
     */
    List<Post> queryAll(@Param("post")Post post);

    /**
     * 新增数据
     *
     * @param post 实例对象
     * @return 影响行数
     */
    int insert(Post post);

    /**
     * 修改数据
     *
     * @param post 实例对象
     * @return 影响行数
     */
    int update(@Param("post")Post post);

    /**
     * 通过主键删除数据
     *
     * @param postId 主键
     * @return 影响行数
     */
    int deleteById(Integer postId);

    /**
     * 通过用户id查询所有帖子，排除匿名的
     *
     * @param pageUtil
     * @param userId
     * @return
     */
    List<Post> queryByUidEexludeAnony(@Param("pageUtil")PageUtil pageUtil, @Param("userId")Integer userId);

    /**
     * 通过用户查帖子，包括匿名的
     *
     * @param pageUtil
     * @param userId
     * @return
     */
    List<Post> queryAllByUser(@Param("pageUtil")PageUtil pageUtil, @Param("userId")Integer userId);

    /**
     * 查询该用户贴子总数
     * @param userId
     * @return
     */
    int queryTotalByUser(Integer userId);

    /**
     * 查询该用户非匿名帖子总数
     * @param userId
     * @return
     */
    int queryTotalByUidEexludeAnony(Integer userId);
}