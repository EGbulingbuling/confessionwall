package org.eg.confessionwall.service;

import org.eg.confessionwall.entity.Answer;
import org.eg.confessionwall.entity.Post;
import org.eg.confessionwall.entity.User;

import java.util.List;

/**
 * 回复表(Answer)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 16:41:18
 */
public interface AnswerService {

    /**
     * 通过ID查询单条数据
     *
     * @param answerId 主键
     * @return 实例对象
     */
    Answer queryById(Integer answerId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Answer> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param answer 实例对象
     * @return 对象列表
     */
    List<Answer> queryAll(Answer answer);

    /**
     * 新增数据
     *
     * @param answer 实例对象
     * @return 是否成功
     */
    boolean insert(Answer answer);

    /**
     * 新增数据
     *
     *
     * @param postId
     * @param replier
     * @param answerContent 回复内容
     * @return 是否成功
     */
    boolean add(Integer postId, User replier, String answerContent);

    /**
     * 修改数据
     *
     * @param answer 实例对象
     * @return 是否成功
     */
    boolean update(Answer answer);

    /**
     * 通过主键删除数据
     *
     * @param answerId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer answerId);

    /**
     * 获取帖子的回复集合
     * @param post
     * @return
     */
    List<Answer> getAnswers(Post post);
}