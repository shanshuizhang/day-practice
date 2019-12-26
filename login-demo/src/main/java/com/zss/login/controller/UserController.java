package com.zss.login.controller;

import com.zss.login.pojo.ApiResult;
import com.zss.login.pojo.CurrentUser;
import com.zss.login.pojo.UserBO;
import com.zss.login.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/10/28 10:29
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    //@Autowired
    //private com.zss.service.UserService userService2;

    @PostMapping("login")
    public ApiResult login(@RequestBody UserBO userBO){
        //userService2.print();
        return new ApiResult(200,"登录成功",userService.buildUserInfo(userBO));
    }

    @GetMapping("user/info")
    public ApiResult<UserBO> info(){
        return new ApiResult(200,"获取用户信息成功",CurrentUser.get());
    }

    @PostMapping("logout")
    public ApiResult logout(@RequestHeader("Authorization") String jwt){
        userService.logout(jwt);
        return new ApiResult(200,"成功",null);
    }
}
