package com.hcy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hcy.entity.User;
import com.hcy.pojo.R;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/1/29 19:16
 */
public interface IUserLoginService extends IService<User> {
    R login(User user);
}
