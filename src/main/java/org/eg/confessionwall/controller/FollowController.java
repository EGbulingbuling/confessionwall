package org.eg.confessionwall.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.eg.confessionwall.entity.Follow;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.FollowService;
import org.eg.confessionwall.service.MessageService;
import org.eg.confessionwall.service.impl.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * (Follow)控制层
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-24 17:42:25
 */
@Controller
@RequestMapping("follow")
@Slf4j
public class FollowController {
    /**
     * 服务对象
     */
    @Resource
    private FollowService followService;
    @Resource
    private RedisService redisService;
    @Resource
    private MessageService messageService;

    /**
     *
     * @param session
     * @param uid 被关注者id
     * @return
     */
    @RequestMapping(value = "following",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> following(HttpSession session, int uid){
        Map<String, Object> map = new HashMap<>();
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 关注用户 {}",user.getUserId(),uid);
        if (followService.add(uid,user)){
//            messageService.sendSysMsg(uid,0,2);添加用户被关注提醒，再议
//            redisService.get(uid);
            map.put("status","0");
            log.info("用户 {} 关注用户 {} 成功",user.getUserId(),uid);
        }else {
            map.put("status","1");
            map.put("msg","关注失败");
            log.warn("用户 {} 关注用户 {} 失败",user.getUserId(),uid);
        }
        return map;
    }

    /**
     *
     * @param session
     * @param uid 被关注者id
     * @return
     */
    @RequestMapping(value = "undo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> undo(HttpSession session, int uid){
        Map<String, Object> map = new HashMap<>();
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 取消关注用户 {}",user.getUserId(),uid);
        if (followService.undo(uid,user)){
            map.put("status","0");
            log.info("用户 {} 取消关注用户 {} 成功",user.getUserId(),uid);
        }else {
            map.put("status","1");
            map.put("msg","取消关注失败");
            log.warn("用户 {} 取消关注用户 {} 失败",user.getUserId(),uid);
        }
        return map;
    }
}