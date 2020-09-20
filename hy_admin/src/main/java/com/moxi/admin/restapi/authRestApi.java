package com.moxi.admin.restapi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moxi.admin.annotion.AuthorityVerify.AuthorityVerify;
import com.moxi.base.enums.EMenuType;
import com.moxi.commons.config.jwt.Audience;
import com.moxi.commons.config.jwt.JwtHelper;
import com.moxi.commons.entity.AuthUser;
import com.moxi.commons.entity.MenuPm;
import com.moxi.utils.*;
import com.moxi.xo.global.MessageConf;
import com.moxi.xo.global.RedisConf;
import com.moxi.xo.global.SQLConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.service.AuthUserService;
import com.moxi.xo.service.MenuPmService;
import com.moxi.xo.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/1 22:37
 */
@RestController
@RequestMapping("/authority")
@Api(value = "登录等权限相关接口", tags = {"登录等权限相关接口"})
@Slf4j
public class authRestApi {

    @Autowired
    MenuPmService permissionService;



    @Autowired
    AuthUserService authUserService;

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private Audience audience;

    @Value(value = "${tokenHead}")
    private String tokenHead;

    @Value(value = "${isRememberMeExpiresSecond}")
    private int longExpiresSecond;

    @ApiOperation(value = "登录", notes = "登录", response = String.class)
    @PostMapping("/login")
    public String login(HttpServletRequest request,
                        @RequestBody UserVO userVO
    ) {
        String username=userVO.getNickName();
        String password=userVO.getPassword();
        int isRememberMe=userVO.getIsRememberMe();
        //0.参数验证
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResultUtil.result(SysConf.ERROR, "账号或密码不能为空");
        }
        //1.查看登录错误次数
        String ip = IpUtils.getIpAddr(request);
        String limitCount = redisUtil.get(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip);
        if (StringUtils.isNotEmpty(limitCount)) {
            Integer tempLimitCount = Integer.valueOf(limitCount);
            if (tempLimitCount >= 5) {
                return ResultUtil.result(SysConf.ERROR, "密码输错次数过多,已被锁定30分钟");
            }
        }
        //2.验证密码正确
        Boolean isEmail= CheckUtils.checkEmail(username);
        Boolean isMobile=CheckUtils.checkMobileNumber(username);
        QueryWrapper<AuthUser> wrapper=new QueryWrapper<>();
        if(isEmail){
            wrapper.eq(SysConf.EMAIL,username);
        }else if(isMobile){
            wrapper.eq(SysConf.PHONE,username);
        }else{
            wrapper.eq(SysConf.NICKNAME,username);
        }
        AuthUser user=authUserService.getOne(wrapper);
        if(user==null){
            return ResultUtil.result(SysConf.ERROR,String.format(MessageConf.LOGIN_ERROR,setLoginCommit(request)));
        }
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        boolean isPassWord=encoder.matches(password,user.getPassword());
        System.out.println(encoder.encode(user.getPassword()));
        if(!isPassWord){
            return ResultUtil.result(SysConf.ERROR, String.format(MessageConf.LOGIN_ERROR, setLoginCommit(request)));
        }
        //3.创建token
        long expiration = isRememberMe == 1 ? longExpiresSecond : audience.getExpiresSecond();
        String jwtToken=jwtHelper.createJWT(
                user.getUid(),
                audience.getClientId(),
                expiration*1000,
                audience.getBase64Secret()
                );
        //4.登录相关操作
        user.setLoginTimes(user.getLoginTimes()+1);
        user.setLoginIp(ip);
        user.setLoginDate(new Date());
        user.updateById();
        //5.结果
        Map<String,Object> map=new HashMap<>();
        map.put(SysConf.TOKEN,jwtToken);
        map.put(SysConf.USER_UID,user.getUid());
        return ResultUtil.result(SysConf.SUCCESS,map);
    }

    @ApiOperation(value = "注册", notes = "注册", response = String.class)
    @PostMapping("/register")
    public String register(HttpServletRequest request,
                        @RequestBody UserVO registered
    ) {
        String mobile = registered.getPhone();
        String userName = registered.getNickName();
        String email = registered.getEmail();
        String passWord = registered.getPassword();
        String validCode = null;

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
            return ResultUtil.result(SysConf.ERROR, "用户名或密码不能为空");
        }

        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(mobile)) {
            return ResultUtil.result(SysConf.ERROR, "邮箱和手机号至少一项不能为空");
        }

        //手机号为空时为邮箱注册 从redis中获取验证码
//        if (StringUtils.isEmpty(mobile) && CheckUtils.checkEmail(email)) {
//            validCode = stringRedisTemplate.opsForValue().get(email);
//        } else if (StringUtils.isEmpty(email) && CheckUtils.checkMobileNumber(mobile)) {
//            validCode = stringRedisTemplate.opsForValue().get(mobile);
//        } else {
//            return ResultUtil.result(SysConf.ERROR, "邮箱或手机号格式有误");
//        }
//        if (validCode.isEmpty()) {
//            return ResultUtil.result(SysConf.ERROR, "验证码已过期");
//        }
//
//        if (code.equals(validCode)) {
//            return ResultUtil.result(SysConf.ERROR, "验证码不正确");
//        }


        QueryWrapper<AuthUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SQLConf.NICK_NAME, userName);
        AuthUser admin = authUserService.getOne(queryWrapper);
        QueryWrapper<AuthUser> wrapper = new QueryWrapper<>();
        if (admin == null) {
            if (StringUtils.isNotEmpty(email)) {
                wrapper.eq(SQLConf.EMAIL, email);
            } else {
                wrapper.eq(SQLConf.MOBILE, mobile);
            }

            if (authUserService.getOne(wrapper) != null) {
                return ResultUtil.result(SysConf.ERROR, "用户账户已存在");
            }
            //密码加密
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            registered.setPassword(encoder.encode(registered.getPassword()));
            AuthUser user=new AuthUser();
            user.setEmail(email);
            user.setPhone(mobile);
            user.setNickname(userName);
            user.setPassword(encoder.encode(registered.getPassword()));
            user.insert();
            //清除redis中的缓存
//            if (StringUtils.isEmpty(mobile)) {
//                stringRedisTemplate.delete(email);
//            } else {
//                stringRedisTemplate.delete(mobile);
//            }
            return ResultUtil.result(SysConf.SUCCESS, "注册成功");
        }
        return ResultUtil.result(SysConf.ERROR, "用户账户已存在");
    }


    @ApiOperation(value = "获取菜单权限列表", notes = "获取菜单权限列表", response = String.class)
    @GetMapping("/getMenu")
    public String getMenu(HttpServletRequest request) {
        //String uid=request.getAttribute(SysConf.ADMIN_UID).toString();
        String uid="3bf3b8298cbe91d5246c511bc6659d99";
        List<MenuPm> menuList=permissionService.getMenuPermissionList(uid);

        Set<MenuPm> parentList=new HashSet<>();
        Set<MenuPm> childCategoryMenuList=new HashSet<>();
        Set<MenuPm> buttonList=new HashSet<>();

        for(MenuPm item:menuList){
            if(item.getMenuType()== EMenuType.MENU&&item.getMenuLevel()==SysConf.ONE){
                parentList.add(item);
            }
            else if(item.getMenuType()==EMenuType.MENU&&item.getMenuLevel()==SysConf.TWO){
                childCategoryMenuList.add(item);
            }
            else if(item.getMenuType()==EMenuType.BUTTON){
                buttonList.add(item);
            }
        }
        //对一级菜单排序
        List<MenuPm> Plist=parentList.stream().sorted().collect(Collectors.toList());
        //对二级菜单排序
        List<MenuPm> Slist=childCategoryMenuList.stream().sorted().collect(Collectors.toList());
        Map<String,Object> map=new HashMap<>();
        map.put(SysConf.PARENT_LIST, Plist);
        map.put(SysConf.SON_LIST, Slist);
        map.put(SysConf.BUTTON_LIST, buttonList);
        return ResultUtil.result(SysConf.SUCCESS, map);
    }

    @AuthorityVerify
    @ApiOperation(value = "测试", notes = "测试", response = String.class)
    @PostMapping("/role")
    public String test() {
        return ResultUtil.result(SysConf.SUCCESS,"success");
    }

    /**
     * 设置登录限制，返回剩余次数
     * 密码错误五次，将会锁定10分钟
     *
     * @param request
     */
    private Integer setLoginCommit(HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        String key=RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip;
        String count = redisUtil.get(key);
        Integer surplusCount = 5;
        if (StringUtils.isNotEmpty(count)) {
            Integer countTemp = Integer.valueOf(count) + 1;
            surplusCount = surplusCount - countTemp;
            redisUtil.setEx(key, String.valueOf(countTemp), 10, TimeUnit.MINUTES);
        } else {
            surplusCount = surplusCount - 1;
            redisUtil.setEx(key, "1", 30, TimeUnit.MINUTES);
        }

        return surplusCount;
    }
}
