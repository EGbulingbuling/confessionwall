package org.eg.confessionwall.service;

import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.entity.Visitor;
import java.util.List;

/**
 * 访客关系多对多(Visitor)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 16:35:57
 */
public interface VisitorService {

    /**
     * 通过ID查询单条数据
     *
     * @param visitId 主键
     * @return 实例对象
     */
    Visitor queryById(Integer visitId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Visitor> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param visitor 实例对象
     * @return 实例对象
     */
    Visitor insert(Visitor visitor);

    /**
     * 修改数据
     *
     * @param visitor 实例对象
     * @return 实例对象
     */
    Visitor update(Visitor visitor);

    /**
     * 查询访客4*3
     * @param user
     * @return
     */
    List<Visitor> queryVisitor(User user);

    /**
     * 访问服务
     * @param visitor 访问者
     * @param visited 被访者
     */
    void visit(User visitor, User visited);
}