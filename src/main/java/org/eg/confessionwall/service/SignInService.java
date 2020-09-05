package org.eg.confessionwall.service;

import org.eg.confessionwall.entity.SignIn;
import org.eg.confessionwall.entity.User;

import java.util.List;

/**
 * (SignIn)表服务接口
 *
 * @author makejava
 * @since 2020-04-11 21:37:23
 */
public interface SignInService {

    /**
     * 通过ID查询单条数据
     *
     * @param signId 主键
     * @return 实例对象
     */
    SignIn queryById(Integer signId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SignIn> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param signIn 实例对象
     * @return 对象列表
     */
    List<SignIn> queryAll(SignIn signIn);

    /**
     * 新增数据
     *
     * @param signIn 实例对象
     * @return 实例对象
     */
    boolean insert(SignIn signIn);

    /**
     * 修改数据
     *
     * @param signIn 实例对象
     * @return 实例对象
     */
    boolean update(SignIn signIn);

    /**
     * 通过主键删除数据
     *
     * @param signId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer signId);

    /**
     * 通过用户id删除数据
     *
     * @param userId 用户id
     * @return 是否成功
     */
    boolean deleteByUserId(Integer userId);

    /**
     * 签到
     * @param user 用户
     * @return 是否成功
     */
    boolean sign(User user);

    /**
     * 是否签到
     * @param user
     * @return
     */
    boolean isSign(User user);

    /**
     * 处理签到中断
     * @param user
     */
    void handleSignInterrupt(User user);
}