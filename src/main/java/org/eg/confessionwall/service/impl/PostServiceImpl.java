package org.eg.confessionwall.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eg.confessionwall.dao.*;
import org.eg.confessionwall.entity.Post;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.PostService;
import org.eg.confessionwall.utils.ImageUtil;
import org.eg.confessionwall.utils.PageUtil;
import org.eg.confessionwall.utils.PostUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * (Post)表服务实现类
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-25 11:52:44
 */
@Service("postService")
@Slf4j
public class PostServiceImpl implements PostService {
    @Resource
    private PostDao postDao;
    @Resource
    private UserDao userDao;
    @Resource
    private FollowDao followDao;
    @Resource
    private AnswerDao answerDao;
    @Resource
    private PostLikeDao postLikeDao;
    @Resource
    private TreasureDao treasureDao;

    /**
     * 通过ID查询单条数据
     *
     * @param postId 主键
     * @return 实例对象
     */
    @Override
    public Post queryById(Integer postId) {
        return this.postDao.queryById(postId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Post> queryAllByLimit(int offset, int limit) {
        return this.postDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询本周热门
     * 查询一周内，评论数最高的前10条帖子
     *
     * @return 对象列表
     */
    @Override
    public List<Post> queryWeekHot() {
        return this.postDao.queryWeekHot();
    }

    /**
     * 查询多条数据
     *
     * @param pageUtil 查询起始位置
     * @return 对象列表
     */
    @Override
    public List<Post> queryAllByTime(PageUtil pageUtil) {
        return this.postDao.queryAllByTime(pageUtil);
    }

    /**
     * 查询最近15天的数据数量
     * @return 数据数量
     */
    @Override
    public int queryTotalByTime() {
        return this.postDao.queryTotalByTime();
    }

    /**
     * 查询关注者的所有帖子
     *
     * @param pageUtil 查询起始位置
     * @return 对象列表
     */
    @Override
    public List<Post> queryFollowingAll(PageUtil pageUtil,User user) {
        List<Integer> following=followDao.queryAllFollowing(user.getUserId());
        if (following.size()>0){
            return this.postDao.queryFollowingAll(pageUtil,following);
        }else {
            return null;
        }
    }

    /**
     * 查询关注者的所有帖子总数
     * @return 数据数量
     */
    @Override
    public int queryFollowingTotal(User user) {
        List<Integer> following=followDao.queryAllFollowing(user.getUserId());
        return this.postDao.queryFollowingTotal(following);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param post 实例对象
     * @return 对象列表
     */
    @Override
    public List<Post> queryAll(Post post) {
        return postDao.queryAll(post);
    }

    /**
     * 新增数据
     *
     * @param post 实例对象
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(Post post,String content,String contentText){
        PostUtil.setAbstract(post,content,contentText);
        //取imgNameList并修改url
        List<String> imgNameList=PostUtil.setContent(post,content);
        //文件复制
        new Thread(()->{
            List<File> fromImgList = ImageUtil.name2file("/usr/local/server/img/cache/",imgNameList);
            List<File> toImgList = ImageUtil.name2file("/usr/local/server/img/",imgNameList);
            try {
                for (int i=0;i<fromImgList.size();i++){
                    FileUtils.copyFile(fromImgList.get(i),toImgList.get(i));
                }
            } catch (IOException e) {
                log.warn("图片转移失败");
            }
        }).start();
        User user=post.getUser();
        if (this.postDao.insert(post) > 0){
            user.setExperience(user.getExperience()+1);
            userDao.update(user);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 修改数据
     *
     * @param post 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(Post post) {
        return this.postDao.update(post) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param postId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer postId) {
        return this.postDao.deleteById(postId) > 0;
    }

    /**
     * 通过用户查帖子，包括匿名的
     *
     * @param pageUtil
     * @param user
     * @return
     */
    @Override
    public List<Post> queryAllByUser(PageUtil pageUtil, User user) {
        return postDao.queryAllByUser(pageUtil,user.getUserId());
    }

    /**
     * 查询该用户贴子总数
     * @param user
     * @return
     */
    @Override
    public int queryTotalByUser(User user) {
        return postDao.queryTotalByUser(user.getUserId());
    }

    /**
     * 查询其他人的帖子，去掉匿名的
     *
     * @param pageUtil
     * @param userId
     * @return
     */
    @Override
    public List<Post> queryOthersPost(PageUtil pageUtil, Integer userId) {
        return postDao.queryByUidEexludeAnony(pageUtil,userId);
    }

    /**
     * 查询该用户非匿名帖子总数
     * @param user
     * @return
     */
    @Override
    public int queryOthersPostTotal(User user) {
        return postDao.queryTotalByUidEexludeAnony(user.getUserId());
    }

    /**
     * 增加访问量
     * @param post
     */
    @Override
    public void visited(Post post) {
        Post temp=new Post();
        temp.setPostId(post.getPostId());
        temp.setPostVisitedCount(post.getPostVisitedCount()+1);
        postDao.update(temp);
    }

    /**
     * 举报
     * 对帖子进行删除
     * @param postId
     * @return
     */
    @Override
    public void tipoff(int postId) throws Exception {
        double d=Math.random();
        if (d<0.2){
            Post post=new Post();
            post.setPostId(postId);
            post.setPostState((byte)2);
            postDao.update(post);
            //应该加入对用户的惩罚，再议
        }else {
            throw new Exception("举报失败");
        }
    }

    /**
     * 删除帖子
     * @param postId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(int postId) {
        Post post=postDao.queryById(postId);
        new Thread(()->{
            List<String> imgNameList=PostUtil.getFileName(post.getPostContent());
            List<File> imgList=ImageUtil.name2file("/usr/local/server/img/",imgNameList);
            for (File img:imgList){
                img.delete();
            }
        }).start();
        answerDao.deleteByPid(postId);
        postLikeDao.deleteByPid(postId);
        treasureDao.deleteByPid(postId);
        postDao.deleteById(postId);
    }
}