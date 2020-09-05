package org.eg.confessionwall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.eg.confessionwall.entity.Answer;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 回复表(Answer)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-24 16:41:18
 */
@Mapper
public interface AnswerDao {

    /**
     * 通过ID查询单条数据
     *
     * @param answerId 主键
     * @return 实例对象
     */
    Answer queryById(Integer answerId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Answer> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param answer 实例对象
     * @return 对象列表
     */
    List<Answer> queryAll(@Param("answer")Answer answer);

    /**
     * 新增数据
     *
     * @param answer 实例对象
     * @return 影响行数
     */
    int insert(Answer answer);

    /**
     * 修改数据
     *
     * @param answer 实例对象
     * @return 影响行数
     */
    int update(@Param("answer")Answer answer);

    /**
     * 通过主键删除数据
     *
     * @param answerId 主键
     * @return 影响行数
     */
    int deleteById(Integer answerId);

    /**
     * 通过主键删除数据
     *
     * @param postId 帖子id
     * @return 影响行数
     */
    void deleteByPid(int postId);
}