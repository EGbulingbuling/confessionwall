package org.eg.confessionwall.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.eg.confessionwall.entity.Treasure;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.TreasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * (Treasure)表控制层
 *
 * @author makejava
 * @since 2020-03-24 18:41:51
 */
@Controller
@RequestMapping("treasure")
@Slf4j
public class TreasureController {
    /**
     * 服务对象
     */
    @Resource
    private TreasureService treasureService;

    /**
     *
     * @param session
     * @param model
     * @param obj
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(HttpSession session, Model model, @RequestBody JSONObject obj){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> map = new HashMap<>();
        int id=Integer.parseInt(obj.getString("id"));//帖子id
        log.info("用户 {} 收藏帖子 {}",user.getUserId(),id);
        if (treasureService.add(id,user)){
            map.put("status","0");
            log.info("用户 {} 收藏帖子 {}成功",user.getUserId(),id);
        }else {
            map.put("status","1");
            map.put("msg","收藏失败");
            log.info("用户 {} 收藏帖子 {}失败",user.getUserId(),id);
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
    @RequestMapping(value = "undo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> undo(HttpSession session, Model model,@RequestBody JSONObject obj){
        Map<String, Object> map = new HashMap<>();
        int id=Integer.parseInt(obj.getString("id"));//帖子id
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 取消收藏 {}",user.getUserId(),id);
        if (treasureService.undo(id,user)){
            map.put("status","0");
            log.info("用户 {} 取消收藏 {}成功",user.getUserId(),id);
        }else {
            map.put("status","1");
            map.put("msg","取消收藏失败");
            log.info("用户 {} 取消收藏 {}失败",user.getUserId(),id);
        }
        return map;
    }
}