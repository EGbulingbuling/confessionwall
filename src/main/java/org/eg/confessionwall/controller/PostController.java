package org.eg.confessionwall.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.eg.confessionwall.entity.Answer;
import org.eg.confessionwall.entity.Post;
import org.eg.confessionwall.entity.Treasure;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.*;
import org.eg.confessionwall.service.impl.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Post)表控制层
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-25 11:52:44
 */
@RequestMapping("post")
@Controller
@Slf4j
public class PostController {
    /**
     * 服务对象
     */
    @Resource
    private PostService postService;
    @Resource
    private AnswerService answerService;
    @Resource
    private TreasureService treasureService;
    @Resource
    private PostLikeService postLikeService;
    @Resource
    private MessageService messageService;
    @Resource
    private RedisService redisService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(HttpSession session, Model model){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        int messageCount = 0;
        try {
            messageCount = redisService.get(user.getUserId());
        } catch (Exception e) {
        }
        model.addAttribute("messageCount",messageCount);
        model.addAttribute("user",user);
        return "jie/add";
    }

    @RequestMapping(value = "goAdd")
    @ResponseBody
    public Map<String, Object> goAdd(HttpSession session, Model model,
                                     @RequestBody JSONObject obj){
        log.info("开始发帖");
        Map<String, Object> map = new HashMap<>();
        int type=obj.getInteger("type");//0表白1分享2吐槽
        int anonymous=obj.getInteger("anonymous");//匿名 0否1是
        String content=obj.getString("content");
        String contentText=obj.getString("contentText");
        Post post=new Post();
        switch (type){
            case 0:{
                if (anonymous==0){
                    post.setPostType(Byte.parseByte("1"));
                }else {
                    post.setPostType(Byte.parseByte("0"));
                }
                break;
            }
            case 1:{
                post.setPostType(Byte.parseByte("2"));
                break;
            }
            case 2:{
                post.setPostType(Byte.parseByte("3"));
                break;
            }
        }
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        post.setUser(user);
        if (postService.add(post,content,contentText)){
            map.put("code","0");
            log.info("用户 {} 发帖成功",user.getUserId());
        }else {
            map.put("code","1");
            map.put("msg","发布失败");
            log.warn("用户 {} 发帖失败",user.getUserId());
        }
        return map;
    }

    /**
     * 需要往视图里传的数据：
     * post
     * answerList
     * isTreasure
     * isLike
     * isMy
     * @return
     *
     */
    @RequestMapping(value = "detail/{postId}", method = RequestMethod.GET)
    public String detail(HttpSession session, Model model,
                         @PathVariable("postId") Integer postId/* 帖子id */){
        //添加帖子
        Post post=postService.queryById(postId);
        if (post!=null){
            model.addAttribute("post",post);
            //answerList
            List<Answer> answerList=answerService.getAnswers(post);
            model.addAttribute("answerList",answerList);
            User user= (User) SecurityUtils.getSubject().getPrincipal();
            if (user!=null){
                int messageCount = 0;
                try {
                    messageCount = redisService.get(user.getUserId());
                } catch (Exception e) {
                }
                model.addAttribute("messageCount",messageCount);

                model.addAttribute("user",user);
                //isTreasure
                if (treasureService.isTreasure(post,user)){
                    model.addAttribute("isTreasure",true);
                }else {
                    model.addAttribute("isTreasure",false);
                }
                //isLike
                if (postLikeService.isLike(postId,user.getUserId())){
                    model.addAttribute("isLike",true);
                }else {
                    model.addAttribute("isLike",false);
                }
                //isMy
                if (post.getUser().getUserId().equals(user.getUserId())){
                    model.addAttribute("isMy",true);
                }else {
                    model.addAttribute("isMy",false);
                    postService.visited(post);//刷新就能增加访问量，为了防刷访问量的话可以将url和refer对比一下
                }
            }else {
                model.addAttribute("isTreasure",false);
                model.addAttribute("isLike",false);
                model.addAttribute("isMy",false);
            }
            return "jie/detail";
        }
        return "/";
    }

    /**
     *
     * @param session
     * @param model
     * @param obj
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(HttpSession session, Model model,@RequestBody JSONObject obj){
        Map<String, Object> map = new HashMap<>();
        int id=Integer.parseInt(obj.getString("id"));//帖子id
        log.info("删除帖子 {}",id);
        try {
            postService.delete(id);
            map.put("status","0");
            log.info("删除帖子 {} 成功",id);
        } catch (Exception e) {
            map.put("status","1");
            map.put("msg","删除失败");
            log.warn("删除帖子 {} 失败",id);
        }
        return map;
    }

    /**
     *
     * @param session
     * @param model
     * @param obj
     * @return
     */
    @RequestMapping(value = "like",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> like(HttpSession session, Model model,@RequestBody JSONObject obj){
        Map<String, Object> map = new HashMap<>();
        int id=Integer.parseInt(obj.getString("id"));//帖子id
        boolean ok=Boolean.parseBoolean(obj.getString("ok"));//是否点过赞
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        if (ok){
            if (postLikeService.undo(id,user.getUserId())){
                map.put("status","0");
                log.info("用户 {} 给帖子 {} 点赞成功",user.getUserId(),id);
            }else {
                map.put("status","1");
                map.put("msg","取消失败");
                log.info("用户 {} 给帖子 {} 点赞失败",user.getUserId(),id);
            }
        }else {
            if (postLikeService.like(id,user.getUserId())){
                map.put("status","0");
                log.info("用户 {} 给帖子 {} 取消点赞成功",user.getUserId(),id);
            }else {
                map.put("status","1");
                map.put("msg","点赞失败");
                log.info("用户 {} 给帖子 {} 取消点赞失败",user.getUserId(),id);
            }
        }
        return map;
    }

    /**
     * 因为没有什么算法审查内容是否违法，所以只要举报就会被封，为了防止有人恶意举报，所以，嘿嘿，50%的概率举报成功
     *
     * 把帖子和发帖人的状态改为封禁状态
     * @param session
     * @param model
     * @param pid
     * @return
     */
    @RequestMapping(value = "tipoff")
    @ResponseBody
    public Map<String, Object> tipoff(HttpSession session, Model model,int pid){
        Map<String, Object> map = new HashMap<>();
        log.info("举报帖子 {}",pid);
        try {
            postService.tipoff(pid);
            log.info("举报帖子 {} 成功",pid);
            Integer uid = postService.queryById(pid).getUser().getUserId();
            messageService.sendSysMsg(uid,pid,1);
            int messageCount=redisService.get(uid);
            redisService.set(uid,++messageCount);
        } catch (Exception e) {
            log.warn("举报帖子 {} 失败",pid);
        }
        map.put("code",0);
        return map;
    }
}