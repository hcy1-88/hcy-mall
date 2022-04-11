package com.hcy.controller;

import com.hcy.entity.User;
import com.hcy.pojo.R;
import com.hcy.service.IUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/1/29 19:13
 */
@Controller
@RequestMapping("/user")
public class UserLoginController {
   @Autowired
   private IUserLoginService userLoginService;
    @RequestMapping("/login")
    @ResponseBody
    public R login(User user) {
        return userLoginService.login(user);
    }
}
