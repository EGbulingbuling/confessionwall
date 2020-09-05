package org.eg.confessionwall.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.eg.confessionwall.entity.*;
import org.eg.confessionwall.service.*;
import org.eg.confessionwall.service.impl.RedisService;
import org.eg.confessionwall.utils.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (User)控制层
 *
 * @author 李旭昊qq:2051190945
 * @since 2020-03-24 17:41:26
 */
@Controller
@RequestMapping("user")
@Slf4j
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;
    @Resource
    private PostService postService;
    @Resource
    private MessageService messageService;
    @Resource
    private TreasureService treasureService;
    @Resource
    private SignInService signInService;
    @Resource
    private FollowService followService;
    @Resource
    private VisitorService visitorService;
    @Resource
    private PostLikeService postLikeService;
    @Resource
    private RedisService redisService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpSession session, Model model){
        return "user/login";
    }

    @RequestMapping(value = "reg", method = RequestMethod.GET)
    public String reg(HttpSession session, Model model){
        return "user/reg";
    }

    @RequestMapping(value = "goLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> goLogin(HttpSession session, Model model,@RequestBody JSONObject obj){
        log.info("登录");
        Map<String, Object> map = new HashMap<>();
        String account=obj.getString("account");
        String password=obj.getString("password");
        UsernamePasswordToken token=new UsernamePasswordToken(account,password);
        Subject currentUser= SecurityUtils.getSubject();
        token.setRememberMe(true);
        try {
            // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            // 所以这一步在调用login(token)方法时,它会走到xxRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            currentUser.login(token);
        } catch (Exception e) {
            token.clear();
            map.put("code",101);
            log.warn("用户 {} 登录失败",account);
            return map;
        }
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 登录成功",user.getUserId());
        userService.logLogin(user);
        map.put("action","/");
        map.put("code",0);

//        User user=new User();
//        user.setAccount(account);
//        List<User> users = userService.queryAll(user);
//        if (users.size()==0){
//            map.put("code",101);
//        }else {
//            if (users.get(0).getPassword().equals(password)){
//                session.setAttribute("user",users.get(0));
//                map.put("action","/");
//                map.put("code",0);
//            }else {
//                map.put("code",101);
//            }
//        }
        return map;
    }

    @RequestMapping(value = "goReg", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> goReg(HttpSession session, Model model,@RequestBody JSONObject obj){
        log.info("注册");
        Map<String, Object> map = new HashMap<>();
        User user=new User();
        user.setAccount(obj.getString("email"));
        user.setPassword(obj.getString("pass"));
        user.setNickname(obj.getString("username"));
        user.setSex(obj.getByte("sex"));
        String repass=obj.getString("repass");
        if (!userService.checkRepass(user,repass)){
            map.put("code",6);
            return map;
        }
        switch (userService.reg(user)){
            case 1:
                map.put("code",1);
                log.warn("用户 {} 注册失败，邮箱已被注册",user.getAccount());
                break;
            case 2:
                map.put("code",2);
                log.warn("用户 {} 注册失败，昵称已被注册",user.getAccount());
                break;
            case 3:
                map.put("code",3);
                log.warn("用户 {} 注册失败，用户insert失败",user.getAccount());
                break;
            case 4:
                map.put("code",4);
                log.info("用户 {} 注册成功",user.getAccount());
                break;
            case 5:
                map.put("code",5);
                log.warn("用户 {} 注册失败，激活邮件发送失败",user.getAccount());
                break;
        }

        return map;
    }

    /**
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "logout")
    public String logout(HttpSession session){
//        session.removeAttribute("user");
        return "/";
    }

    /**
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "set", method = RequestMethod.GET)
    public String set(HttpSession session, Model model){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        int messageCount = 0;
        try {
            messageCount = redisService.get(user.getUserId());
        } catch (Exception e) {
        }
        model.addAttribute("messageCount",messageCount);
        model.addAttribute("user",user);
        log.info("用户 {} 进入设置页面",user.getUserId());
        return "user/set";
    }

    /**
     * 需传参数：treasureList
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(HttpSession session, Model model){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        int messageCount = 0;
        try {
            messageCount = redisService.get(user.getUserId());
        } catch (Exception e) {
        }
        model.addAttribute("messageCount",messageCount);
        model.addAttribute("user",user);
        log.info("用户 {} 进入用户index",user.getUserId());
        Treasure treasure=new Treasure();
        treasure.setUser(user);
        List<Treasure> treasureList=treasureService.queryAll(treasure);
        model.addAttribute("treasureList",treasureList);
        return "user/index";
    }

    /**
     * 需传参数messageList
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "message", method = RequestMethod.GET)
    public String message(HttpSession session, Model model){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        int messageCount = 0;
        try {
            messageCount = redisService.get(user.getUserId());
        } catch (Exception e) {
        }
        model.addAttribute("messageCount",messageCount);
        model.addAttribute("user",user);
        log.info("用户 {} 进入消息页",user.getUserId());
        PageUtil pageUtil=new PageUtil(1,5);
        List<Message> messageList=messageService.queryAll(pageUtil,user);
        int messageTotal;
        if (messageList.size()==0){
            messageTotal=0;
        }else {
            messageTotal=messageService.queryTotal(user);
        }
        pageUtil.setTotalCount(messageTotal);
        session.setAttribute("pageUtil",pageUtil);
        model.addAttribute("messageList",messageList);
        return "user/message";
    }

    /**
     * 需要传递参数：postList
     * user
     * isMe
     * visitorList
     * likedNum
     * collectedNum
     * followedNum
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(HttpSession session, Model model){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        int messageCount = 0;
        try {
            messageCount = redisService.get(user.getUserId());
        } catch (Exception e) {
        }
        model.addAttribute("messageCount",messageCount);
        model.addAttribute("user",user);
        log.info("用户 {} 进入用户home",user.getUserId());
        PageUtil pageUtil=new PageUtil(1,13);
        List<Post> postList=postService.queryAllByUser(pageUtil,user);
        int postTotal;
        if (postList==null){
            postTotal=0;
        }else {
            postTotal=postService.queryTotalByUser(user);
        }
        pageUtil.setTotalCount(postTotal);

        List<Visitor> visitorList=visitorService.queryVisitor(user);
        session.setAttribute("pageUtil",pageUtil);
        int likedNum=postLikeService.queryLikedNum(user);
        int collectedNum=treasureService.queryTreasuredNum(user);
        int followedNum=followService.queryFollowedNum(user);
        model.addAttribute("postList",postList);
        model.addAttribute("visited",user);//被访问者
        model.addAttribute("isMe",true);
        model.addAttribute("visitorList",visitorList);
        model.addAttribute("likedNum",likedNum);
        model.addAttribute("collectedNum",collectedNum);
        model.addAttribute("followedNum",followedNum);
        return "user/home";
    }

    /**
     * 需要传递参数：postList(去除匿名帖子)
     * user
     * isMe
     * isFollow
     * likedNum
     * collectedNum
     * followedNum
     * @param session
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping(value = "home/{userId}", method = RequestMethod.GET)
    public String otherHome(HttpSession session, Model model,
                            @PathVariable("userId") Integer userId/* 被访者id */){
        User visitor=(User) SecurityUtils.getSubject().getPrincipal();
        if (userId.equals(visitor.getUserId())){
            return "redirect:/user/home";
        }
        int messageCount = 0;
        try {
            messageCount = redisService.get(visitor.getUserId());
        } catch (Exception e) {
        }
        model.addAttribute("messageCount",messageCount);
        model.addAttribute("user",visitor);
        User user=userService.queryById(userId);//被访问者
        log.info("用户 {} 访问用户 {} home",visitor.getUserId(),user.getUserId());
        PageUtil pageUtil=new PageUtil(1,13);
        List<Post> postList=postService.queryOthersPost(pageUtil,user.getUserId());
        int postTotal;
        if (postList==null){
            postTotal=0;
        }else {
            postTotal=postService.queryOthersPostTotal(user);
        }
        pageUtil.setTotalCount(postTotal);

        int likedNum=postLikeService.queryLikedNum(user);
        int collectedNum=treasureService.queryTreasuredNum(user);
        int followedNum=followService.queryFollowedNum(user);

        visitorService.visit(visitor,user);//可以放在切面里

        session.setAttribute("pageUtil",pageUtil);
        model.addAttribute("postList",postList);
        model.addAttribute("visited",user);//被访问者
        model.addAttribute("isMe",false);
        model.addAttribute("likedNum",likedNum);
        model.addAttribute("collectedNum",collectedNum);
        model.addAttribute("followedNum",followedNum);
        if (followService.isFollow(visitor.getUserId(),userId)){
            model.addAttribute("isFollow",true);
        }else {
            model.addAttribute("isFollow",false);
        }
        return "user/home";
    }

    /**
     * 签到
     * @return
     */
    @RequestMapping(value = "sign", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> signIn(HttpSession session, Model model){
        Map<String, Object> map=new HashMap<>();
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 签到",user.getUserId());
        if (signInService.sign(user)){

            Subject subject = SecurityUtils.getSubject();
            PrincipalCollection principalCollection = subject.getPrincipals();
            String realmName = principalCollection.getRealmNames().iterator().next();
            PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
            //重新加载Principal
            subject.runAs(newPrincipalCollection);

//            session.setAttribute("user",user);
            map.put("code",0);
            log.info("用户 {} 签到成功",user.getUserId());
        }else {
            map.put("code",1);
            log.warn("用户 {} 签到失败",user.getUserId());
        }

        return map;
    }

    /**
     * 头像上传
     * @param mf
     * @return
     */
    @RequestMapping(value = "/uploadHead")
    @ResponseBody
    public Map<String, Object> uploadHead(HttpSession session,@RequestParam(value = "file") MultipartFile mf){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 上传头像",user.getUserId());
        Map<String, Object> map = new HashMap<>();

        try {
            userService.uploadHead(user,mf);

            Subject subject = SecurityUtils.getSubject();
            PrincipalCollection principalCollection = subject.getPrincipals();
            String realmName = principalCollection.getRealmNames().iterator().next();
            PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
            //重新加载Principal
            subject.runAs(newPrincipalCollection);

            map.put("status",0);
            log.info("用户 {} 上传头像成功",user.getUserId());
        } catch (Exception e) {
            log.warn("用户 {} 上传头像失败",user.getUserId());
            map.put("status",1);
            map.put("msg","上传失败");
        }

        return map;
    }

    @RequestMapping(value = "set/nickname", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setNickname(HttpSession session, Model model,@RequestBody JSONObject obj){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 修改昵称",user.getUserId());
        Map<String, Object> map = new HashMap<>();
        String username=obj.getString("username");
        if ("".equals(username)){
            map.put("code",11);
            return map;
        }
        if (!userService.checkNicknameRepeat(username)){
            if (userService.modifyNickname(user,username)){
                map.put("code",0);
                log.info("用户 {} 修改昵称成功",user.getUserId());
            }else {
                map.put("code",10);
                log.warn("用户 {} 修改昵称失败",user.getUserId());
            }
        }else {
            map.put("code",1);
        }
        return map;
    }

    @RequestMapping(value = "set/phone", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setPhone(HttpSession session, Model model,@RequestBody JSONObject obj){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 修改手机号",user.getUserId());
        Map<String, Object> map = new HashMap<>();
        String phone=obj.getString("phone");
        if ("".equals(phone)){
            map.put("code",11);
            return map;
        }
        if (!userService.checkPhoneRepeat(phone)){
            if (userService.modifyPhone(user,phone)){
                map.put("code",0);
                log.info("用户 {} 修改手机号成功",user.getUserId());
            }else {
                map.put("code",10);
                log.warn("用户 {} 修改手机号失败",user.getUserId());
            }
        }else {
            map.put("code",2);
        }
        return map;
    }

    @RequestMapping(value = "set/autograph", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setAutograph(HttpSession session, Model model,@RequestBody JSONObject obj){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 修改签名",user.getUserId());
        Map<String, Object> map = new HashMap<>();
        String autograph=obj.getString("autograph");

        if (userService.modifyAutograph(user,autograph)){
            map.put("code",0);
            log.info("用户 {} 修改签名成功",user.getUserId());
        }else {
            map.put("code",10);
            log.warn("用户 {} 修改签名失败",user.getUserId());
        }
        return map;
    }

    @RequestMapping(value = "rePassword", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> rePassword(HttpSession session, Model model,@RequestBody JSONObject obj){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        log.info("用户 {} 修改密码",user.getUserId());
        Map<String, Object> map = new HashMap<>();
        String nowpass=obj.getString("nowpass");
        String pass=obj.getString("pass");
        String repass=obj.getString("repass");
        switch (userService.checkPass(user,nowpass,pass,repass)){
            case 1:
                map.put("code",1);
                break;
            case 2:
                map.put("code",2);
                break;
            case 3:
                map.put("code",3);
                break;
            case 4:
                map.put("code",4);
                break;
            default:
                if (userService.modifyPass(user,pass)){
                    map.put("code",0);
                    log.info("用户 {} 修改密码成功",user.getUserId());
                }else {
                    map.put("code",10);
                }
                break;
        }
        return map;
    }
}