package org.eg.confessionwall.service.impl;

import org.eg.confessionwall.dao.UserDao;
import org.eg.confessionwall.entity.SignIn;
import org.eg.confessionwall.dao.SignInDao;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.SignInService;
import org.eg.confessionwall.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * (SignIn)表服务实现类
 *
 * @author makejava
 * @since 2020-04-11 21:37:23
 */
@Service("signInService")
public class SignInServiceImpl implements SignInService {
    @Resource
    private SignInDao signInDao;
    @Resource
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param signId 主键
     * @return 实例对象
     */
    @Override
    public SignIn queryById(Integer signId) {
        return this.signInDao.queryById(signId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SignIn> queryAllByLimit(int offset, int limit) {
        return this.signInDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param signIn 实例对象
     * @return 对象列表
     */
    @Override
    public List<SignIn> queryAll(SignIn signIn) {
        return signInDao.queryAll(signIn);
    }

    /**
     * 新增数据
     *
     * @param signIn 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(SignIn signIn) {
        return this.signInDao.insert(signIn)>0;
    }

    /**
     * 修改数据
     *
     * @param signIn 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(SignIn signIn) {
        return this.signInDao.update(signIn)>0;
    }

    /**
     * 通过主键删除数据
     *
     * @param signId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer signId) {
        return this.signInDao.deleteById(signId) > 0;
    }

    /**
     * 通过用户id删除数据
     *
     * @param userId 用户id
     * @return 是否成功
     */
    @Override
    public boolean deleteByUserId(Integer userId) {
        return this.signInDao.deleteByUserId(userId)>0;
    }

    /**
     * 签到
     * @param user 用户
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean sign(User user) {
        signInDao.deleteByUserId(user.getUserId());
        SignIn signIn=new SignIn();
        signIn.setUserId(user.getUserId());
        if (signInDao.insert(signIn)>0){
            User temp=new User();
            temp.setSignedCount(user.getSignedCount()+1);
            user.setSignedCount(user.getSignedCount()+1);//需要改
            temp.setUserId(user.getUserId());
            if (user.getSignedCount()<7){
                temp.setExperience(user.getExperience()+(user.getSignedCount()+1));
                user.setExperience(user.getExperience()+(user.getSignedCount()+1));
            }else {
                temp.setExperience(user.getExperience()+7);
                user.setExperience(user.getExperience()+7);
            }
            return userDao.update(temp)>0;
        }else {
            return false;
        }
    }

    /**
     * 是否签到
     * @param user
     * @return
     */
    @Override
    public boolean isSign(User user) {
        SignIn signIn=new SignIn();
        signIn.setUserId(user.getUserId());
        List<SignIn> signIns = signInDao.queryAll(signIn);
        if (signIns.size()>0){
            return DateUtil.isSameDay(new Date(),signIns.get(0).getSignTime());
        }else {
            return false;
        }
    }

    /**
     * 处理签到中断
     * @param user
     */
    @Override
    public void handleSignInterrupt(User user) {
        SignIn signIn=new SignIn();
        signIn.setUserId(user.getUserId());
        List<SignIn> signIns = signInDao.queryAll(signIn);
        if (signIns.size()>0){
            if (isInterrupt(signIns.get(0).getSignTime())){
                User temp=new User();
                temp.setUserId(user.getUserId());
                temp.setSignedCount(0);
                user.setSignedCount(0);//需要改
                userDao.update(temp);
            }
        }
    }

    /**
     *
     * @param SignTime
     * @return
     */
    private boolean isInterrupt(Date SignTime) {
        Calendar yesterday = Calendar.getInstance();
        yesterday.setTime(new Date());
        yesterday.add(Calendar.DATE, -1);
        Calendar lastSignTime=Calendar.getInstance();
        lastSignTime.setTime(SignTime);
        return !DateUtil.isSameDay(yesterday,lastSignTime);
    }
}