package org.eg.confessionwall.controller;

import org.eg.confessionwall.entity.SignIn;
import org.eg.confessionwall.service.SignInService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SignIn)表控制层
 *
 * @author makejava
 * @since 2020-04-11 21:37:24
 */
@RestController
@RequestMapping("signIn")
public class SignInController {
    /**
     * 服务对象
     */
    @Resource
    private SignInService signInService;
}