package org.eg.confessionwall.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.eg.confessionwall.entity.Notice;
import org.eg.confessionwall.entity.Post;
import org.eg.confessionwall.entity.SignIn;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.NoticeService;
import org.eg.confessionwall.service.PostService;
import org.eg.confessionwall.service.SignInService;
import org.eg.confessionwall.service.UserService;
import org.eg.confessionwall.service.impl.RedisService;
import org.eg.confessionwall.utils.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-24 17:41:26
 */
@Controller
@Slf4j
public class PageController {
    @Resource
    private PostService postService;
    @Resource
    private NoticeService noticeService;
    @Resource
    private UserService userService;
    @Resource
    private SignInService signInService;
    @Resource
    private RedisService redisService;
    /**
     * 需要往视图里传的数据：
     * postList 帖子集合(分页，每页13个)
     *  按时间选取最新的帖子
     * noticeList 公告集合（固定5个）
     *  按时间选取最新的公告
     * user 当前用户
     * hotList 本周热议帖子集合（固定10个）
     *  选取一周内回复数最高的帖子
     *  isSignIn 今日是否签到。
     * @return
     *
     */
    @RequestMapping(value = {"/","index"}, method = RequestMethod.GET)
    public String index(HttpSession session, Model model){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        //添加帖子集合
        PageUtil pageUtil=new PageUtil(1,13);
        List<Post> postList=postService.queryAllByTime(pageUtil);
        int postTotal=postService.queryTotalByTime();
        pageUtil.setTotalCount(postTotal);
        model.addAttribute("postList",postList);
        session.setAttribute("pageUtil",pageUtil);
        //添加公告集合
        List<Notice> noticeList=noticeService.queryAllByLimit(0,5);
        model.addAttribute("noticeList",noticeList);
        //添加本周热议帖子集合
        List<Post> hotList=postService.queryWeekHot();
        model.addAttribute("hotList",hotList);
        //添加是否签到
        if (user!=null){
            int messageCount = 0;
            try {
                messageCount = redisService.get(user.getUserId());
            } catch (Exception e) {
            }
            model.addAttribute("messageCount",messageCount);

            if (signInService.isSign(user)){
                model.addAttribute("isSignIn",true);
            }else {
                model.addAttribute("isSignIn",false);
                signInService.handleSignInterrupt(user);
            }
            user.setPassword("");
            model.addAttribute("user",user);
        }

        return "index";
    }

    /**
     * 需要往视图里传的数据：
     * postList 关注的人的帖子集合(分页，每页13个)
     *  按时间选取最新的帖子
     * noticeList 公告集合（固定5个）
     *  按时间选取最新的公告
     * user 当前用户
     * hotList 本周热议帖子集合（固定10个）
     *  选取一周内回复数最高的帖子
     *  isSignIn 今日是否签到。
     * @return
     *
     */
    @RequestMapping(value = {"following"}, method = RequestMethod.GET)
    public String following(HttpSession session, Model model){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        int messageCount = 0;
        try {
            messageCount = redisService.get(user.getUserId());
        } catch (Exception e) {
        }
        model.addAttribute("messageCount",messageCount);
        log.info("进入关注页");
        //添加帖子集合
        PageUtil pageUtil=new PageUtil(1,13);
        List<Post> postList=postService.queryFollowingAll(pageUtil,user);
        int postTotal;
        if (postList==null){
            postTotal=0;
        }else {
            postTotal=postService.queryFollowingTotal(user);
        }
        pageUtil.setTotalCount(postTotal);
        model.addAttribute("postList",postList);

        session.setAttribute("pageUtil",pageUtil);
        //添加公告集合
        List<Notice> noticeList=noticeService.queryAllByLimit(0,5);
        model.addAttribute("noticeList",noticeList);
        //添加本周热议帖子集合
        List<Post> hotList=postService.queryWeekHot();
        model.addAttribute("hotList",hotList);

        //添加是否签到
        if (signInService.isSign(user)){
            model.addAttribute("isSignIn",true);
        }else {
            model.addAttribute("isSignIn",false);
            signInService.handleSignInterrupt(user);
        }
        user.setPassword("");
        model.addAttribute("user",user);
        return "jie/following";
    }

    /**
     * 需要返回的数据：
     * postList
     * pageUtil
     * @return
     *
     */
    @RequestMapping(value = "morePost", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> morePost(HttpSession session, Model model, HttpServletRequest request){
        Map<String, Object> map=new HashMap<>();
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        PageUtil pageUtil= (PageUtil) session.getAttribute("pageUtil");
        pageUtil.nextPage();
        List<Post> postList = null;
        String referer[] = request.getHeader("referer").split("/");
        String uri=referer[referer.length-1];
        switch (uri){
            case "localhost:8080":
            case "39.96.93.83":
            case "index":
                log.info("获取更多帖子,当前第 {} 页",pageUtil.getCurrpageNum());
                postList=postService.queryAllByTime(pageUtil);
                break;
            case "following":
                log.info("获取更多关注者的帖子,当前第 {} 页",pageUtil.getCurrpageNum());
                postList=postService.queryFollowingAll(pageUtil,user);
                break;
            case "home":
                log.info("获取更多自己发过的帖子,当前第 {} 页",pageUtil.getCurrpageNum());
                postList=postService.queryAllByUser(pageUtil,user);
                break;
            default:
                int userId=Integer.parseInt(uri);
                log.info("获取更多当前被访问者的帖子,当前第 {} 页",pageUtil.getCurrpageNum());
                postList=postService.queryOthersPost(pageUtil,userId);
                break;
        }
//        if ("following".equals(uri)){
//            postList=postService.queryFollowingAll(pageUtil,user);
//        }else {
//            postList=postService.queryAllByTime(pageUtil);
//        }
        map.put("postList", JSONArray.parseArray(JSON.toJSONString(postList, SerializerFeature.DisableCircularReferenceDetect)));
        map.put("pageUtil",pageUtil);
        session.setAttribute("pageUtil",pageUtil);
        return map;
    }
}
