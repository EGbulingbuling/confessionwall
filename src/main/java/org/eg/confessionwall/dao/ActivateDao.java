package org.eg.confessionwall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.eg.confessionwall.entity.Activate;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Activate)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-12 19:08:09
 */
@Mapper
public interface ActivateDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 用户id
     * @return 实例对象
     */
    Activate queryById(Integer userId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Activate> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param activate 实例对象
     * @return 对象列表
     */
    List<Activate> queryAll(Activate activate);

    /**
     * 新增数据
     *
     * @param activate 实例对象
     * @return 影响行数
     */
    int insert(Activate activate);

    /**
     * 修改数据
     *
     * @param activate 实例对象
     * @return 影响行数
     */
    int update(Activate activate);

    /**
     * 通过主键删除数据
     *
     * @param userId 用户id
     * @return 影响行数
     */
    int deleteById(Integer userId);

}