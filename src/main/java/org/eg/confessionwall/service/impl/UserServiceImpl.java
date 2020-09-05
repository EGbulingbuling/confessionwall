package org.eg.confessionwall.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.eg.confessionwall.dao.ActivateDao;
import org.eg.confessionwall.entity.Activate;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.dao.UserDao;
import org.eg.confessionwall.service.UserService;
import org.eg.confessionwall.utils.MailUtil;
import org.eg.confessionwall.utils.UploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 16:33:40
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private ActivateDao activateDao;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer userId) {
        return this.userDao.queryById(userId);
    }

    /**
     * 通过账号查询用户
     *
     * @param account 账号
     * @return 实例对象
     */
    @Override
    public User queryByAccount(String account) {
        return this.userDao.queryByAccount(account);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    @Override
    public List<User> queryAll(User user) {
        return userDao.queryAll(user);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(User user) {
        return this.userDao.insert(user)>0;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(User user) {
        return this.userDao.update(user)>0;
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer userId) {
        return this.userDao.deleteById(userId) > 0;
    }

    /**
     * 检查密码是否合法
     * @param user 当前用户
     * @param nowpass 用户输入的当前密码
     * @param pass 要修改的密码
     * @param repass 重新输入的密码
     * @return 1:新密码和重复密码不同
     *          2：当前密码错误
     *          3：新密码与当前密码相同
     *          4：密码不符合要求（6到16个字符）
     */
    @Override
    public int checkPass(User user, String nowpass, String pass, String repass) {
        SimpleHash nowpassSh = new SimpleHash("MD5", nowpass, user.getSalt(),5);
        String password=userDao.queryById(user.getUserId()).getPassword();
        if (!pass.equals(repass)){
            return 1;
        }else if (!nowpassSh.toHex().equals(password)){
            return 2;
        }else if (nowpass.equals(pass)){
            return 3;
        }else if (pass.length()<6||pass.length()>16){
            return 4;
        }else {
            return 0;
        }
    }

    /**
     * 修改密码
     * @param user
     * @param pass
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyPass(User user, String pass) {
        String salt= UUID.randomUUID().toString();
        SimpleHash passSh=new SimpleHash("md5",pass,salt,5);
        user.setSalt(salt);
        user.setPassword(passSh.toHex());
        if (userDao.update(user)>0){

            Subject subject = SecurityUtils.getSubject();
            PrincipalCollection principalCollection = subject.getPrincipals();
            String realmName = principalCollection.getRealmNames().iterator().next();
            PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
            //重新加载Principal
            subject.runAs(newPrincipalCollection);

            return true;
        }else {
            return false;
        }
    }

    /**
     * 注册用户
     * @param user
     * @return 1:邮箱已被注册 2:昵称已被注册 3:注册失败 4:注册成功 5:激活邮件发送失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int reg(User user) {
        if (userDao.queryByAccount(user.getAccount())!=null){
            return 1;
        }else if (userDao.checkNickname(user.getNickname())>0){
            return 2;
        }else {
            String salt= UUID.randomUUID().toString();
            SimpleHash passSh=new SimpleHash("md5",user.getPassword(),salt,5);
            user.setSalt(salt);
            user.setPassword(passSh.toHex());
//            user.setHead("http://localhost:8080/res/images/avatar/00.jpg");
            user.setHead("http://39.96.93.83:80/res/images/avatar/00.jpg");
            user.setLastLoginTime(new Date());
            if (userDao.insert(user)>0){
                Activate activate=new Activate();
                activate.setUserId(userDao.queryByAccount(user.getAccount()).getUserId());
                activate.setActivationCode(UUID.randomUUID().toString());
                if (activateDao.insert(activate)>0){
                    Map<String,String> activateInfo=new HashMap<>();
                    activateInfo.put("uid",activate.getUserId().toString());
                    activateInfo.put("activationCode",activate.getActivationCode());
//                    try {
//                        MailUtil.send(user.getAccount(),activateInfo);//耗费时间有点长，另开一个线程
//                    } catch (Exception e) {
//                        log.info("向用户 {} 发送激活邮件失败",user.getUserId());
//                        activateDao.deleteById(user.getUserId());
//                        userDao.deleteById(user.getUserId());
//                        return 5;
//                    }
                    new Thread(()->{//谨慎，线程什么的早忘了
                        try {
                            MailUtil.send(user.getAccount(),activateInfo);
                        } catch (Exception e) {
                            log.info("用户 {} 激活邮件发送失败",user.getUserId());
                            activateDao.deleteById(user.getUserId());
                            userDao.deleteById(user.getUserId());
                        }
                    }).start();
                    return 4;
                }else {
                    return 3;
                }
            }else {
                return 3;
            }
        }
    }

    /**
     * 上传头像
     * @param user
     * @param mf
     */
    @Override
    public void uploadHead(User user, MultipartFile mf) throws Exception {
        String originalHeadUrl=user.getHead();
        String uploadFileName= UploadUtil.uploadSimpleFile(mf,"head/");
        user.setHead(uploadFileName);
        log.info("用户 {} 头像url:{}",user.getUserId(),user.getHead());
        int row=userDao.update(user);
        if (row==0){
            throw new Exception();
        }else{
            String[] originalHeads=originalHeadUrl.split("/");
            String headFileName=originalHeads[originalHeads.length-1];
            File originalHead=new File("/usr/local/server/head/",headFileName);
//            File originalHead=new File("D:/server/head/",headFileName);
            originalHead.delete();
        }
    }

    /**
     * 检查重复密码是否正确
     * @param user
     * @param repass
     * @return
     */
    @Override
    public boolean checkRepass(User user, String repass) {
        return repass.equals(user.getPassword());
    }

    /**
     * 记录用户登录
     * @param user
     */
    @Override
    public void logLogin(User user) {
        User temp=new User();
        temp.setUserId(user.getUserId());
        temp.setLastLoginTime(new Date());
        userDao.update(temp);
    }

    /**
     * 检查用户名重复
     * @param username
     * @return
     */
    @Override
    public boolean checkNicknameRepeat(String username) {
        return username!=null&&!"".equals(username.trim())&&userDao.checkNickname(username.trim())>0;
    }

    /**
     * 修改用户名
     * @param user
     * @param username
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyNickname(User user, String username) {
        User temp=new User();
        temp.setUserId(user.getUserId());
        temp.setNickname(username);
        if (userDao.update(temp)>0){
            user.setNickname(username);
            updatePrincipal(user);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 检查手机号是否重复
     * @param phone
     * @return
     */
    @Override
    public boolean checkPhoneRepeat(String phone) {
        return phone!=null&&!"".equals(phone.trim())&&userDao.checkPhone(phone.trim())>0;
    }

    /**
     * 修改手机号
     * @param user
     * @param phone
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyPhone(User user, String phone) {
        User temp=new User();
        temp.setUserId(user.getUserId());
        temp.setPhone(phone);
        if (userDao.update(temp)>0){
            user.setPhone(phone);
            updatePrincipal(user);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 修改签名
     * @param user
     * @param autograph
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyAutograph(User user, String autograph) {
        User temp=new User();
        temp.setUserId(user.getUserId());
        temp.setAutograph(autograph);
        if (userDao.update(temp)>0){
            user.setAutograph(autograph);
            updatePrincipal(user);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 更新身份信息
     * @param user
     */
    public void updatePrincipal(User user){
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        //重新加载Principal
        subject.runAs(newPrincipalCollection);
    }
}