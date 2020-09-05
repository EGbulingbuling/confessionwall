package org.eg.confessionwall.controller;

import org.eg.confessionwall.entity.Visitor;
import org.eg.confessionwall.service.VisitorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Visitor)控制层
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-24 17:41:58
 */
@RestController
@RequestMapping("visitor")
public class VisitorController {
    /**
     * 服务对象
     */
    @Resource
    private VisitorService visitorService;
}