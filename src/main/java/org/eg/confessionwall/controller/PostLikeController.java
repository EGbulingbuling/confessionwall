package org.eg.confessionwall.controller;

import org.eg.confessionwall.entity.PostLike;
import org.eg.confessionwall.service.PostLikeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (PostLike)表控制层
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-04-20 13:16:05
 */
@RestController
@RequestMapping("postLike")
public class PostLikeController {
    /**
     * 服务对象
     */
    @Resource
    private PostLikeService postLikeService;

}