package org.eg.confessionwall.service;

import org.eg.confessionwall.entity.Post;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.utils.PageUtil;

import java.util.List;

/**
 * (Post)表服务接口
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-25 11:52:44
 */
public interface PostService {

    /**
     * 通过ID查询单条数据
     *
     * @param postId 主键
     * @return 实例对象
     */
    Post queryById(Integer postId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Post> queryAllByLimit(int offset, int limit);

    /**
     * 查询本周热门
     * 查询一周内，评论数最高的前10条帖子
     *
     * @return 对象列表
     */
    List<Post> queryWeekHot();

    /**
     * 查询多条数据
     *
     * @param pageUtil 查询起始位置
     * @return 对象列表
     */
    List<Post> queryAllByTime(PageUtil pageUtil);

    /**
     * 查询最近15天的数据数量
     * @return 数据数量
     */
    int queryTotalByTime();

    /**
     * 查询关注者的所有帖子
     * @param pageUtil
     * @return
     */
    List<Post> queryFollowingAll(PageUtil pageUtil,User user);

    /**
     * 查询关注者的所有帖子总数
     * @return
     */
    int queryFollowingTotal(User user);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param post 实例对象
     * @return 对象列表
     */
    List<Post> queryAll(Post post);

    /**
     * 新增数据
     *
     * @param post 实例对象
     * @return 实例对象
     */
    boolean add(Post post,String content,String contentText);

    /**
     * 修改数据
     *
     * @param post 实例对象
     * @return 实例对象
     */
    boolean update(Post post);

    /**
     * 通过主键删除数据
     *
     * @param postId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer postId);

    /**
     * 通过用户查帖子，包括匿名的
     *
     * @param pageUtil
     * @param user
     * @return
     */
    List<Post> queryAllByUser(PageUtil pageUtil, User user);

    /**
     * 查询该用户贴子总数
     * @param user
     * @return
     */
    int queryTotalByUser(User user);

    /**
     * 查询该用户非匿名帖子
     *
     * @param pageUtil
     * @param userId
     * @return
     */
    List<Post> queryOthersPost(PageUtil pageUtil, Integer userId);

    /**
     * 查询该用户非匿名帖子总数
     * @param user
     * @return
     */
    int queryOthersPostTotal(User user);

    /**
     * 增加访问量
     * @param post
     */
    void visited(Post post);

    /**
     * 举报
     * @param postId
     * @return
     */
    void tipoff(int postId) throws Exception;

    /**
     * 删除帖子
     * @param postId
     * @return
     */
    void delete(int postId);
}