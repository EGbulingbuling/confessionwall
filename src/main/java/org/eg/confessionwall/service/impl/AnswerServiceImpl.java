package org.eg.confessionwall.service.impl;

import org.eg.confessionwall.dao.PostDao;
import org.eg.confessionwall.dao.UserDao;
import org.eg.confessionwall.entity.Answer;
import org.eg.confessionwall.dao.AnswerDao;
import org.eg.confessionwall.entity.Post;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.AnswerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 回复表(Answer)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 16:41:18
 */
@Service("answerService")
public class AnswerServiceImpl implements AnswerService {
    @Resource
    private AnswerDao answerDao;
    @Resource
    private UserDao userDao;
    @Resource
    private PostDao postDao;

    /**
     * 通过ID查询单条数据
     *
     * @param answerId 主键
     * @return 实例对象
     */
    @Override
    public Answer queryById(Integer answerId) {
        return this.answerDao.queryById(answerId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Answer> queryAllByLimit(int offset, int limit) {
        return this.answerDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param answer 实例对象
     * @return 对象列表
     */
    @Override
    public List<Answer> queryAll(Answer answer) {
        return this.answerDao.queryAll(answer);
    }

    /**
     * 新增数据
     *
     * @param answer 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(Answer answer) {
        return this.answerDao.insert(answer)>0;
    }

    /**
     *
     * @param postId
     * @param replier
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(Integer postId, User replier, String answerContent) {
        Answer answer=new Answer();
        Post post=new Post();
        post.setPostId(postId);
        answer.setPost(post);
        answer.setUser(replier);
        if (answerContent.startsWith("@")&&answerContent.contains(" ")&&answerContent.indexOf(" ")<21){
            //获取名字就从@开始到前20个字符里的第一个空格，获取到就获取，获取不到拉倒
            String senderName=answerContent.substring(1,answerContent.indexOf(" "));
            User user=new User();
            user.setNickname(senderName);
            List<User> users = userDao.queryAll(user);
            if (users.size()>0){
                answer.setAnswerContent(answerContent.substring(answerContent.indexOf(" ")+1));
                answer.setRespondent(users.get(0));
            }else {
                answer.setAnswerContent(answerContent);
            }
        }else {
            answer.setAnswerContent(answerContent);
        }
        if (answerDao.insert(answer)>0){
            Post temp=postDao.queryById(postId);
            temp.setAnswerCount(temp.getAnswerCount()+1);
            User user=temp.getUser();
            user.setExperience(user.getExperience()+1);
            userDao.update(user);
            postDao.update(temp);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 修改数据
     *
     * @param answer 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Answer answer) {
        return this.answerDao.update(answer)>0;
    }

    /**
     * 通过主键删除数据
     *
     * @param answerId 主键
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Integer answerId) {
        Answer answer=new Answer();
        answer.setAnswerId(answerId);
        List<Answer> answers = answerDao.queryAll(answer);
        if (answers.size()>0){
            answer=answers.get(0);
        }
        if (this.answerDao.deleteById(answerId) > 0){
            Post temp=answer.getPost();
            temp.setAnswerCount(temp.getAnswerCount()-1);
            postDao.update(temp);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取帖子的回复集合
     * @param post
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Answer> getAnswers(Post post) {
        Answer answer=new Answer();
        answer.setPost(post);
        return answerDao.queryAll(answer);
    }
}