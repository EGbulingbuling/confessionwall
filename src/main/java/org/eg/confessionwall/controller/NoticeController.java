package org.eg.confessionwall.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.eg.confessionwall.entity.Notice;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.NoticeService;
import org.eg.confessionwall.service.impl.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * (Notice)控制层
 *
 * @author makejava
 * @since 2020-03-24 17:42:11
 */
@Controller
@RequestMapping("notice")
@Slf4j
public class NoticeController {
    /**
     * 服务对象
     */
    @Resource
    private NoticeService noticeService;
    @Resource
    private RedisService redisService;

    @GetMapping(value = "detail/{noticeId}")
    public String detail(HttpSession session, Model model,
                         @PathVariable("noticeId") Integer noticeId/* 帖子id */) {
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        int messageCount = 0;
        try {
            messageCount = redisService.get(user.getUserId());
        } catch (Exception e) {
        }
        model.addAttribute("messageCount",messageCount);
        model.addAttribute("user",user);
        log.info("查看公告 {}",noticeId);
        Notice notice=noticeService.queryById(noticeId);
        model.addAttribute("notice",notice);
        return "notice/notice";
    }
}