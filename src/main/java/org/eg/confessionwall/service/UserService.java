package org.eg.confessionwall.service;

import org.eg.confessionwall.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 16:33:39
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    User queryById(Integer userId);

    /**
     * 通过账号查询用户
     *
     * @param account 账号
     * @return 实例对象
     */
    User queryByAccount(String account);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    List<User> queryAll(User user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    boolean insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    boolean update(User user);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer userId);

    /**
     * 检查密码是否合法
     * @param user
     * @param nowpass
     * @param pass
     * @param repass
     * @return
     */
    int checkPass(User user, String nowpass, String pass, String repass);

    /**
     * 修改密码
     * @param user
     * @param pass
     * @return
     */
    boolean modifyPass(User user, String pass);

    /**
     * 注册用户
     * @param user
     * @return
     */
    int reg(User user);

    /**
     * 上传头像
     * @param user
     * @param mf
     */
    void uploadHead(User user, MultipartFile mf) throws Exception;

    /**
     * 检查重复密码是否正确
     * @param user
     * @param repass
     * @return
     */
    boolean checkRepass(User user, String repass);

    /**
     * 记录用户登录
     * @param user
     */
    void logLogin(User user);

    /**
     * 检查用户名重复
     * @param username
     * @return
     */
    boolean checkNicknameRepeat(String username);

    /**
     * 修改用户名
     * @param user
     * @param username
     * @return
     */
    boolean modifyNickname(User user, String username);

    /**
     * 检查手机号是否重复
     * @param phone
     * @return
     */
    boolean checkPhoneRepeat(String phone);

    /**
     * 修改手机号
     * @param user
     * @param phone
     * @return
     */
    boolean modifyPhone(User user, String phone);

    /**
     * 修改签名
     * @param user
     * @param autograph
     * @return
     */
    boolean modifyAutograph(User user, String autograph);
}