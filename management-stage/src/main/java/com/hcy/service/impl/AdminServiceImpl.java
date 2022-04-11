package com.hcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.hcy.dto.GoodsForAdmin;
import com.hcy.entity.Admin;
import com.hcy.feign.GoodsFeign;
import com.hcy.mapper.AdminMapper;
import com.hcy.pojo.R;
import com.hcy.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcy.util.IdWorker;
import com.hcy.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hcy
 * @since 2021-10-27
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GoodsFeign goodsFeign;

    @Override
    @Transactional
    public R addAdmin(Admin admin) {
        admin.setId(Long.toString(idWorker.nextId()));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        int i = this.baseMapper.insert(admin);
        Map<String,Object> res = new HashMap<>();
        if (i==1) {
//            System.out.println(admin);
            res.put("msg","添加成功！");
            return new R(1000, true, res);
        }
        res.put("msg","出问题了！未注册成功！");
        return new R(5000,false,res);
    }

    @Override
    public R login(String email, String password) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        Admin admin = this.baseMapper.selectOne(queryWrapper);
        // 第一个参数是 用户输入的原生密码，未加工的密码，第二个才是数据库加密后的密码
        // 密码器会将二者自动进行匹配
        boolean matches = passwordEncoder.matches(password, admin.getPassword());
        Map<String,Object> res = new HashMap<>();
        if (matches){
            Map<String,Object> map = new HashMap<>();
            map.put("name",admin.getName());
            map.put("email",admin.getEmail());
            String token = JwtTokenUtil.getToken(map);
            res.put("token",token);
            res.put("currentMan",admin);
            return new R(1000,true,res);
        }
        res.put("msg","登录失败");
        return new R(5000,false,res);
    }

    @Override
    public R modifyCheck(int skuId, int status) {
        return goodsFeign.modifySkuStatus(skuId,status);
    }

    @Override
    public R findAllForCheck() {
        List<GoodsForAdmin> allGoods = goodsFeign.findAllGoods();
        Map<String,Object> res = new HashMap<>();
        res.put("goodsList",allGoods);
        R r = new R(1000, true, res);
        return r;
    }


}
