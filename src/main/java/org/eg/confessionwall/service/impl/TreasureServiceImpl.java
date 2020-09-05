package org.eg.confessionwall.service.impl;

import org.eg.confessionwall.entity.Post;
import org.eg.confessionwall.entity.Treasure;
import org.eg.confessionwall.dao.TreasureDao;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.TreasureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Treasure)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 18:41:51
 */
@Service("treasureService")
public class TreasureServiceImpl implements TreasureService {
    @Resource
    private TreasureDao treasureDao;

    /**
     * 通过ID查询单条数据
     *
     * @param treasureId 主键
     * @return 实例对象
     */
    @Override
    public Treasure queryById(Integer treasureId) {
        return this.treasureDao.queryById(treasureId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Treasure> queryAllByLimit(int offset, int limit) {
        return this.treasureDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param treasure 实例对象
     * @return 对象列表
     */
    @Override
    public List<Treasure> queryAll(Treasure treasure) {
        return treasureDao.queryAll(treasure);
    }

    /**
     * 新增数据
     *
     * @param treasure 实例对象
     * @return 实例对象
     */
    @Override
    public Treasure insert(Treasure treasure) {
        this.treasureDao.insert(treasure);
        return treasure;
    }

    /**
     * 修改数据
     *
     * @param treasure 实例对象
     * @return 实例对象
     */
    @Override
    public Treasure update(Treasure treasure) {
        this.treasureDao.update(treasure);
        return this.queryById(treasure.getTreasureId());
    }

    /**
     *
     * @param postId 帖子id
     * @param user 当前用户
     * @return
     */
    @Override
    public boolean add(int postId, User user) {
        Post post=new Post();
        post.setPostId(postId);
        Treasure treasure=new Treasure();
        treasure.setPost(post);
        treasure.setUser(user);
        return treasureDao.insert(treasure)>0;
    }

    /**
     *
     * @param postId 帖子id
     * @param user 当前用户
     * @return
     */
    @Override
    public boolean undo(int postId, User user) {

        return treasureDao.deleteByPidUid(postId,user.getUserId())>0;
    }

    /**
     * 查询用户被收藏次数
     * @param user
     * @return
     */
    @Override
    public int queryTreasuredNum(User user) {
        return treasureDao.queryTreasuredNum(user.getUserId());
    }

    /**
     * 判断是否收藏
     * @param post
     * @param user
     * @return
     */
    @Override
    public boolean isTreasure(Post post, User user) {
        Treasure treasure=new Treasure();
        treasure.setPost(post);
        treasure.setUser(user);
        return treasureDao.queryAll(treasure).size()>0;
    }
}