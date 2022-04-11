package com.hcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hcy.dto.GoodsForSeller;
import com.hcy.entity.Seller;
import com.hcy.feign.GoodsFeign;
import com.hcy.mapper.SellerMapper;
import com.hcy.pojo.R;
import com.hcy.service.SellerService;
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
public class SellerServiceImpl extends ServiceImpl<SellerMapper, Seller> implements SellerService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GoodsFeign goodsFeign;

    @Override
    @Transactional
    public R addSeller(Seller seller) {
        seller.setId(Long.toString(idWorker.nextId()));
        seller.setPassword(passwordEncoder.encode(seller.getPassword()));
        int i = this.baseMapper.insert(seller);
        Map<String,Object> res = new HashMap<>();
        if (i==1) {
            res.put("msg", "添加成功！");
            return new R(1000, true, res);
        }
        res.put("msg","添加失败！");
        return new R(5000,false,res);
    }

    @Override
    public R login(String email, String password) {
        QueryWrapper<Seller> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        Seller seller = this.baseMapper.selectOne(queryWrapper);
        System.out.println("前台的密码："+password);
        System.out.println("后台的密码："+seller.getPassword());
        boolean matches = passwordEncoder.matches(password, seller.getPassword());
        Map<String,Object> res = new HashMap<>();
        if (matches){
            Map<String,Object> map = new HashMap<>();
            map.put("name",seller.getName());
            map.put("email",seller.getEmail());
            String token = JwtTokenUtil.getToken(map);
            System.out.println("token:"+token);
            res.put("token",token);
            res.put("currentMan",seller);
            return new R(1000,true,res);
        }
        res.put("msg","登录失败！");
        return new R(5000,false,res);
    }
}
