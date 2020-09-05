package org.eg.confessionwall.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.eg.confessionwall.entity.Message;
import org.eg.confessionwall.entity.Post;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.MessageService;
import org.eg.confessionwall.service.impl.RedisService;
import org.eg.confessionwall.utils.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息，多对多(Message)表控制层
 *
 * @author makejava
 * @since 2020-03-26 15:53:03
 */
@Controller
@RequestMapping("message")
@Slf4j
public class MessageController {
    /**
     * 服务对象
     */
    @Resource
    private MessageService messageService;
    @Resource
    private RedisService redisService;

    @RequestMapping("addMessage/{userId}")
    public String addMessage(HttpSession session, Model model,@PathVariable("userId") Integer userId){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        int messageCount = 0;
        try {
            messageCount = redisService.get(user.getUserId());
        } catch (Exception e) {
        }
        model.addAttribute("messageCount",messageCount);
        model.addAttribute("user",user);
        model.addAttribute("userId",userId);
        return "message/add";
    }

    @RequestMapping(value = "send", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> send(HttpSession session, Model model,@RequestBody JSONObject obj,HttpServletRequest request){
        Integer uid=obj.getInteger("uid");
        String message=obj.getString("message");
        Map<String, Object> map=new HashMap<>();
        User sender= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 给用户 {} 发送信息",sender.getUserId(),uid);
        if (messageService.send(sender.getUserId(),uid,message)){
            int messageCount= 0;
            try {
                messageCount = redisService.get(uid);
            } catch (Exception e) {
            }
            redisService.set(uid,++messageCount);
            map.put("code",0);
            map.put("url",request.getHeader("referer"));
            log.info("用户 {} 给用户 {} 发送信息成功",sender.getUserId(),uid);
        }else {
            map.put("code",1);
            log.warn("用户 {} 给用户 {} 发送信息失败",sender.getUserId(),uid);
        }
        return map;
    }

    @RequestMapping(value = "alreadyRead", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> alreadyRead(HttpSession session, Model model){
        Map<String, Object> map=new HashMap<>();
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 信息全部已读",user.getUserId());
        if (messageService.read(user)){
            redisService.delete(user.getUserId());
            map.put("code",0);
            log.info("用户 {} 信息全部已读成功",user.getUserId());
        }else {
            map.put("code",110);
            log.warn("用户 {} 信息全部已读失败",user.getUserId());
        }
        return map;
    }

    /**
     * 需要返回的数据：
     * messageList
     * pageUtil
     * @return
     *
     */
    @RequestMapping(value = "moreMessage", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> moreMessage(HttpSession session, Model model, HttpServletRequest request){
        Map<String, Object> map=new HashMap<>();
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        PageUtil pageUtil= (PageUtil) session.getAttribute("pageUtil");
        pageUtil.nextPage();
        log.info("用户 {} 获取更多信息，当前第 {} 页",user.getUserId(),pageUtil.getCurrpageNum());
        List<Message> messageList;
        messageList=messageService.queryAll(pageUtil,user);
        map.put("messageList", JSONArray.parseArray(JSON.toJSONString(messageList, SerializerFeature.DisableCircularReferenceDetect)));
        map.put("pageUtil",pageUtil);
        session.setAttribute("pageUtil",pageUtil);
        return map;
    }
}