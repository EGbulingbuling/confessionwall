package org.eg.confessionwall.controller;

import lombok.extern.slf4j.Slf4j;
import org.eg.confessionwall.service.impl.FileUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-29 12:52:44
 */
@Controller
@Slf4j
public class ImageController {
    @Resource
    private FileUploadService fileUploadService;

    /*
     * 返回图片路径（富文本）
     */
    @RequestMapping(value = "/selectJpgUrl")
    @ResponseBody
    public Map<String, Object> selectJpgUrl(@RequestParam(value = "imgFile") MultipartFile mf, HttpServletRequest request){

        log.info("上传图片");
        Map<String, Object> map = new HashMap<>();
        String fileUrl="";
        try {
            fileUrl=fileUploadService.uploadOnEcsCache(mf);
            log.info("文件上传在ecs上，url为 {}",fileUrl);
        } catch (Exception e) {
            log.info("图片上传失败");
            return null;
        }
        map.put("data",fileUrl);
        return map;
    }

    /*
     * 返回图片路径（富文本）
     */
    @RequestMapping(value = "/uploadNoticeImg")
    @ResponseBody
    public Map<String, Object> uploadNoticeImg(@RequestParam(value = "imgFile") MultipartFile mf){

        log.info("上传公告图片");
        Map<String, Object> map = new HashMap<>();
        String fileUrl="";
        try {
            fileUrl=fileUploadService.uploadOnEcs(mf);
            log.info("公告图片上传在ecs上，url为 {}",fileUrl);
        } catch (Exception e) {
            log.info("公告图片上传失败");
            return null;
        }
        map.put("data",fileUrl);
        return map;
    }
}