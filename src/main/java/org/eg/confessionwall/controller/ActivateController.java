package org.eg.confessionwall.controller;

import lombok.extern.slf4j.Slf4j;
import org.eg.confessionwall.entity.Activate;
import org.eg.confessionwall.service.ActivateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Activate)表控制层
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-05-12 19:08:09
 */
@Controller
@RequestMapping("activate")
@Slf4j
public class ActivateController {
    /**
     * 服务对象
     */
    @Resource
    private ActivateService activateService;

    /**
     *
     * @param model
     * @param t 用户id
     * @param a 激活码
     * @return
     */
    @GetMapping("goActivate")
    public String goActivate(Model model,int t,String a){
        log.info("用户 {} 开始激活账号",t);
        switch (activateService.activate(t,a)){
            case 1:
                model.addAttribute("code",1);
                model.addAttribute("msg","激活已过期，请重新注册");
                log.warn("用户 {} 激活失败，激活码过期",t);
                break;
            case 2:
                model.addAttribute("code",2);
                model.addAttribute("msg","该账号已激活");
                log.warn("用户 {} 激活失败，该用户已激活",t);
                break;
            case 3:
                model.addAttribute("code",3);
                model.addAttribute("msg","激活码错误");
                log.warn("用户 {} 激活失败，激活码错误",t);
                break;
            case 4:
                model.addAttribute("code",4);
                model.addAttribute("msg","激活成功(￣▽￣)~*");
                log.warn("用户 {} 开始激活账号成功",t);
                break;
        }
        return "notice/activateResult";
    }
}