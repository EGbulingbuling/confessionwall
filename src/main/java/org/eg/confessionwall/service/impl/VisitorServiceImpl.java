package org.eg.confessionwall.service.impl;

import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.entity.Visitor;
import org.eg.confessionwall.dao.VisitorDao;
import org.eg.confessionwall.service.VisitorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 访客关系多对多(Visitor)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 16:35:57
 */
@Service("visitorService")
public class VisitorServiceImpl implements VisitorService {
    @Resource
    private VisitorDao visitorDao;

    /**
     * 通过ID查询单条数据
     *
     * @param visitId 主键
     * @return 实例对象
     */
    @Override
    public Visitor queryById(Integer visitId) {
        return this.visitorDao.queryById(visitId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Visitor> queryAllByLimit(int offset, int limit) {
        return this.visitorDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param visitor 实例对象
     * @return 实例对象
     */
    @Override
    public Visitor insert(Visitor visitor) {
        this.visitorDao.insert(visitor);
        return visitor;
    }

    /**
     * 修改数据
     *
     * @param visitor 实例对象
     * @return 实例对象
     */
    @Override
    public Visitor update(Visitor visitor) {
        this.visitorDao.update(visitor);
        return this.queryById(visitor.getVisitId());
    }

    /**
     * 查询访客4*3
     * @param user
     * @return
     */
    @Override
    public List<Visitor> queryVisitor(User user) {
        Visitor visitor=new Visitor();
        visitor.setVisited(user);
        return visitorDao.queryRecent(visitor);
    }

    /**
     * 访问服务
     * @param visitor 访问者
     * @param visited 被访者
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void visit(User visitor, User visited) {
        visitorDao.deleteById(visitor.getUserId(),visited.getUserId());
        Visitor visit=new Visitor();
        visit.setVisitor(visitor);
        visit.setVisited(visited);
        visitorDao.insert(visit);
    }
}