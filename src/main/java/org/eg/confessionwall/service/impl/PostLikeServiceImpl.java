package org.eg.confessionwall.service.impl;

import org.eg.confessionwall.dao.PostDao;
import org.eg.confessionwall.dao.UserDao;
import org.eg.confessionwall.entity.Post;
import org.eg.confessionwall.entity.PostLike;
import org.eg.confessionwall.dao.PostLikeDao;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.PostLikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PostLike)表服务实现类
 *
 * @author makejava
 * @since 2020-04-20 13:16:05
 */
@Service("postLikeService")
public class PostLikeServiceImpl implements PostLikeService {
    @Resource
    private PostLikeDao postLikeDao;
    @Resource
    private PostDao postDao;
    @Resource
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param likeId 主键
     * @return 实例对象
     */
    @Override
    public PostLike queryById(Integer likeId) {
        return this.postLikeDao.queryById(likeId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<PostLike> queryAllByLimit(int offset, int limit) {
        return this.postLikeDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param postLike 实例对象
     * @return 实例对象
     */
    @Override
    public PostLike insert(PostLike postLike) {
        this.postLikeDao.insert(postLike);
        return postLike;
    }

    /**
     * 修改数据
     *
     * @param postLike 实例对象
     * @return 实例对象
     */
    @Override
    public PostLike update(PostLike postLike) {
        this.postLikeDao.update(postLike);
        return this.queryById(postLike.getLikeId());
    }

    /**
     * 通过帖子用户查询是否点赞
     *
     * @param postId 帖子id
     * @param userId 用户id
     * @return 是否点赞
     */
    @Override
    public boolean isLike(Integer postId, Integer userId) {
        PostLike postLike=new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        return postLikeDao.queryAll(postLike).size()>0;
    }

    /**
     *
     * @param postId 帖子id
     * @param userId 用户id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean like(Integer postId, Integer userId) {
        PostLike postLike=new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        if (postLikeDao.insert(postLike)>0){
            Post post=postDao.queryById(postId);
            post.setPostLikedCount(post.getPostLikedCount()+1);
            User user=post.getUser();
            user.setExperience(user.getExperience()+1);
            userDao.update(user);
            postDao.update(post);
            return true;
        }else {
            return false;
        }
    }

    /**
     *
     * @param postId 帖子id
     * @param userId 用户id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean undo(Integer postId, Integer userId) {
        if (postLikeDao.deleteByPidUid(postId,userId)>0){
            Post post=postDao.queryById(postId);
            post.setPostLikedCount(post.getPostLikedCount()-1);
            postDao.update(post);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 查询用户被赞次数
     * @param user
     * @return
     */
    @Override
    public int queryLikedNum(User user) {
        Post post=new Post();
        post.setUser(user);
        List<Post> postList = postDao.queryAll(post);
        int likedNum=0;
        for (Post temp : postList){
            likedNum=likedNum+temp.getPostLikedCount();
        }
        return likedNum;
    }
}