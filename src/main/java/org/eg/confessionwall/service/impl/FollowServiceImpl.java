package org.eg.confessionwall.service.impl;

import org.eg.confessionwall.entity.Follow;
import org.eg.confessionwall.dao.FollowDao;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.FollowService;
import org.eg.confessionwall.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Follow)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 16:40:09
 */
@Service("followService")
public class FollowServiceImpl implements FollowService {
    @Resource
    private FollowDao followDao;
    @Resource
    private UserService userService;

    /**
     * 通过ID查询单条数据
     *
     * @param followId 主键
     * @return 实例对象
     */
    @Override
    public Follow queryById(Integer followId) {
        return this.followDao.queryById(followId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Follow> queryAllByLimit(int offset, int limit) {
        return this.followDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param follow 实例对象
     * @return 实例对象
     */
    @Override
    public Follow insert(Follow follow) {
        this.followDao.insert(follow);
        return follow;
    }

    /**
     * 修改数据
     *
     * @param follow 实例对象
     * @return 实例对象
     */
    @Override
    public Follow update(Follow follow) {
        this.followDao.update(follow);
        return this.queryById(follow.getFollowId());
    }

    /**
     *
     * @param follower 关注者
     * @param followed 被关注者
     * @return
     */
    @Override
    public boolean isFollow(Integer follower, Integer followed) {
        User user1=new User();
        User user2=new User();
        Follow follow=new Follow();
        user1.setUserId(follower);
        user2.setUserId(followed);
        follow.setFollower(user1);
        follow.setFollowed(user2);
        return followDao.queryAll(follow).size()>0;
    }

    /**
     *
     * @param uid 被关注者
     * @param user 关注者
     * @return
     */
    @Override
    public boolean add(int uid, User user) {
        Follow follow=new Follow();
        follow.setFollower(user);
        try {
            follow.setFollowed(userService.queryById(uid));
        } catch (Exception e) {
            return false;
        }
        return followDao.insert(follow)>0;
    }

    /**
     *
     * @param uid 被关注者
     * @param user 关注者
     * @return
     */
    @Override
    public boolean undo(int uid, User user) {
        return followDao.deleteById(user.getUserId(),uid)>0;
    }

    /**
     * 查询用户的粉丝数量
     * @param user
     * @return
     */
    @Override
    public int queryFollowedNum(User user) {
        return followDao.queryFollowedNum(user.getUserId());
    }
}