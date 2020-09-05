package org.eg.confessionwall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.eg.confessionwall.entity.Treasure;
import org.apache.ibatis.annotations.Param;
import org.eg.confessionwall.entity.User;

import java.util.List;

/**
 * (Treasure)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-24 18:41:51
 */
@Mapper
public interface TreasureDao {

    /**
     * 通过ID查询单条数据
     *
     * @param treasureId 主键
     * @return 实例对象
     */
    Treasure queryById(Integer treasureId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Treasure> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param treasure 实例对象
     * @return 对象列表
     */
    List<Treasure> queryAll(@Param("treasure") Treasure treasure);

    /**
     * 新增数据
     *
     * @param treasure 实例对象
     * @return 影响行数
     */
    int insert(Treasure treasure);

    /**
     * 修改数据
     *
     * @param treasure 实例对象
     * @return 影响行数
     */
    int update(Treasure treasure);

    /**
     * 通过主键删除数据
     *
     * @param postId
     * @param userId
     * @return
     */
    int deleteByPidUid(Integer postId,Integer userId);

    /**
     * 查询用户被收藏次数
     * @param userId
     * @return
     */
    int queryTreasuredNum(Integer userId);

    /**
     *
     * @param postId
     */
    void deleteByPid(int postId);
}