package org.eg.confessionwall.service;

import org.eg.confessionwall.entity.Activate;
import java.util.List;

/**
 * (Activate)表服务接口
 *
 * @author makejava
 * @since 2020-05-12 19:08:09
 */
public interface ActivateService {

    /**
     * 通过ID查询单条数据
     *
     * @param activateId 主键
     * @return 实例对象
     */
    Activate queryById(Integer activateId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Activate> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param activate 实例对象
     * @return 实例对象
     */
    Activate insert(Activate activate);

    /**
     * 修改数据
     *
     * @param activate 实例对象
     * @return 实例对象
     */
    Activate update(Activate activate);

    /**
     * 通过主键删除数据
     *
     * @param activateId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer activateId);

    /**
     * 激活
     * @param uid
     * @param activationCode
     * @return
     */
    int activate(int uid, String activationCode);
}