package org.eg.confessionwall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.eg.confessionwall.entity.SignIn;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SignIn)表数据库访问层
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-04-11 21:37:22
 */
@Mapper
public interface SignInDao {

    /**
     * 通过ID查询单条数据
     *
     * @param signId 主键
     * @return 实例对象
     */
    SignIn queryById(Integer signId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SignIn> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


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
     * @return 影响行数
     */
    int insert(SignIn signIn);

    /**
     * 修改数据
     *
     * @param signIn 实例对象
     * @return 影响行数
     */
    int update(SignIn signIn);

    /**
     * 通过主键删除数据
     *
     * @param signId 主键
     * @return 影响行数
     */
    int deleteById(Integer signId);

    /**
     * 通过用户id删除数据
     *
     * @param userId 用户id
     * @return 影响行数
     */
    int deleteByUserId(Integer userId);
}