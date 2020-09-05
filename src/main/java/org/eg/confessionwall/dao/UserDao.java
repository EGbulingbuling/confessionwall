package org.eg.confessionwall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.eg.confessionwall.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-24 16:33:39
 */
@Mapper
public interface UserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    User queryById(Integer userId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


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
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int update(User user);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Integer userId);

    /**
     * 查询该昵称的数量
     * @param nickname
     * @return
     */
    int checkNickname(String nickname);

    /**
     * 查询该电话的数量
     * @param phone
     * @return
     */
    int checkPhone(String phone);

    /**
     * 通过账号查询用户
     *
     * @param account 账号
     * @return 实例对象
     */
    User queryByAccount(String account);
}