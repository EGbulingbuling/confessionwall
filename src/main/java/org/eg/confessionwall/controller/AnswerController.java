package org.eg.confessionwall.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.eg.confessionwall.entity.Answer;
import org.eg.confessionwall.entity.Post;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.AnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * (Answer)控制层
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-24 17:42:36
 */
@Controller
@RequestMapping("answer")
@Slf4j
public class AnswerController {
    /**
     * 服务对象
     */
    @Resource
    private AnswerService answerService;

    /**
     *
     *
     * @param session
     * @param model
     * @param obj
     * @param postId
     * @return
     */
    @RequestMapping(value = "goAdd/{postId}",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> goAdd(HttpSession session, Model model,
                                     @RequestBody JSONObject obj,@PathVariable("postId") Integer postId/* 帖子id */){
        User replier= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 对帖子 {} 回复",replier.getUserId(),postId);
        Map<String, Object> map = new HashMap<>();
        String answerContent=obj.getString("answerContent");
        if (answerService.add(postId,replier,answerContent)){
            map.put("code","0");
            log.info("用户 {} 对帖子 {} 回复成功",replier.getUserId(),postId);
        }else {
            map.put("code","1");
            map.put("msg","回复失败");
            log.warn("用户 {} 对帖子 {} 回复失败",replier.getUserId(),postId);
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
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(HttpSession session, Model model,@RequestBody JSONObject obj){
        Map<String, Object> map = new HashMap<>();
        int id=Integer.parseInt(obj.getString("id"));//回复id
        log.info("删除回复 {}",id);
        if (answerService.deleteById(id)){
            map.put("status","0");
            log.info("删除回复 {} 成功",id);
        }else {
            map.put("status","1");
            map.put("msg","删除失败");
            log.warn("删除回复 {} 失败",id);
        }
        map.put("status","0");
        return map;
    }
}