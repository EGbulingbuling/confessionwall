package org.eg.confessionwall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.eg.confessionwall.entity.Visitor;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 访客关系多对多(Visitor)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-24 16:35:57
 */
@Mapper
public interface VisitorDao {

    /**
     * 通过ID查询单条数据
     *
     * @param visitId 主键
     * @return 实例对象
     */
    Visitor queryById(Integer visitId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Visitor> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param visitor 实例对象
     * @return 对象列表
     */
    List<Visitor> queryAll(@Param("visitor") Visitor visitor);

    /**
     * 新增数据
     *
     * @param visitor 实例对象
     * @return 影响行数
     */
    int insert(@Param("visitor") Visitor visitor);

    /**
     * 修改数据
     *
     * @param visitor 实例对象
     * @return 影响行数
     */
    int update(Visitor visitor);

    /**
     *
     * @param visitorId
     * @param visitedId
     * @return
     */
    int deleteById(Integer visitorId,Integer visitedId);

    /**
     * 查询最近的访客 12
     * @param visitor
     * @return
     */
    List<Visitor> queryRecent(@Param("visitor") Visitor visitor);
}