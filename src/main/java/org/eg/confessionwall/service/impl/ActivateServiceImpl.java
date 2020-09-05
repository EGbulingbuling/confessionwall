package org.eg.confessionwall.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.eg.confessionwall.dao.UserDao;
import org.eg.confessionwall.entity.Activate;
import org.eg.confessionwall.dao.ActivateDao;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.ActivateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (Activate)表服务实现类
 *
 * @author makejava
 * @since 2020-05-12 19:08:09
 */
@Service("activateService")
@Slf4j
public class ActivateServiceImpl implements ActivateService {
    @Resource
    private ActivateDao activateDao;
    @Resource
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param activateId 主键
     * @return 实例对象
     */
    @Override
    public Activate queryById(Integer activateId) {
        return this.activateDao.queryById(activateId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Activate> queryAllByLimit(int offset, int limit) {
        return this.activateDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param activate 实例对象
     * @return 实例对象
     */
    @Override
    public Activate insert(Activate activate) {
        this.activateDao.insert(activate);
        return activate;
    }

    /**
     * 修改数据
     *
     * @param activate 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Activate update(Activate activate) {
        this.activateDao.update(activate);
        return this.queryById(activate.getActivateId());
    }

    /**
     * 通过主键删除数据
     *
     * @param activateId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer activateId) {
        return this.activateDao.deleteById(activateId) > 0;
    }

    /**
     * 激活
     * @param uid
     * @param activationCode
     * @return 1:激活过期 2:已激活 3:激活码错误 4:激活成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int activate(int uid, String activationCode) {
        Activate activate=activateDao.queryById(uid);
        if (checkTimeOut(activate)){
            activateDao.deleteById(uid);
            userDao.deleteById(uid);
            return 1;
        }else if (isActivated(activate)){
            return 2;
        }else if (!checkActivationCode(activate,activationCode)){
            return 3;
        }else {
            User user=new User();
            user.setUserId(uid);
            user.setUserState((byte) 2);
            activate.setActivateState((byte) 2);
            activateDao.update(activate);
            userDao.update(user);
            return 4;
        }
    }

    /**
     * 检查激活码是否正确
     * @param activate
     * @param activationCode
     * @return 是否正确
     */
    private boolean checkActivationCode(Activate activate, String activationCode) {
        if (activationCode!=null&& !"".equals(activationCode) &&activate.getActivationCode()!=null&&!"".equals(activate.getActivationCode())){
            return activationCode.equals(activate.getActivationCode());
        }else {
            return false;
        }
    }

    /**
     * 检查是否已经激活
     * @param activate
     * @return 是否激活
     */
    private boolean isActivated(Activate activate) {
        return activate.getActivateState()==(byte)2;
    }

    /**
     * 检查激活码是否过期
     * @param activate
     * @return 是否过期
     */
    private boolean checkTimeOut(Activate activate) {
        Date current=new Date();
        long currentTime=current.getTime();
        long activateTime=activate.getActivateTime().getTime();
        int hours=(int)((currentTime-activateTime)/(1000*60*60));
        return hours>24;
    }
}