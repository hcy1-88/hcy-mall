package com.hcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcy.entity.User;
import com.hcy.mapper.UserLoginMapper;
import com.hcy.pojo.R;
import com.hcy.service.IUserLoginService;
import com.hcy.util.JwtTokenUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/1/29 19:18
 */
@Service
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper,User> implements IUserLoginService {
    @Override
    public R login(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",user.getName());
        User uu = this.baseMapper.selectOne(queryWrapper);
        Map<String,Object> res = new HashMap<>();
        R r = new R();
        if (uu == null){
            r.setCode(1001);
            r.setSuccess(false);
            res.put("msg","用户名或密码错误！");
            r.setRes(res);
        }else{
            if (uu.getPassword().equals(user.getPassword())){
                r.setCode(1001);
                r.setSuccess(true);
                res.put("msg","登录成功！");
                Map<String,Object> map = new HashMap<>();
                map.put("id",uu.getId());
                map.put("name",uu.getName());
                map.put("phone",uu.getPhone());
                map.put("email",uu.getEmail());
                String token = JwtTokenUtil.getToken(map);
                res.put("id",uu.getId());
                res.put("name",uu.getName());
                res.put("phone",uu.getPhone());
                res.put("email",uu.getEmail());
                res.put("token",token);
                r.setRes(res);
            }else{
                r.setCode(1001);
                r.setSuccess(false);
                res.put("msg","用户名或密码错误！");
                r.setRes(res);
            }
        }
        return r;
    }
}
