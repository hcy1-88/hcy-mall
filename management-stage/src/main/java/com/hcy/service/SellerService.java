package com.hcy.service;

import com.hcy.entity.Admin;
import com.hcy.entity.Seller;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hcy.pojo.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hcy
 * @since 2021-10-27
 */
public interface SellerService extends IService<Seller> {
    R addSeller(Seller seller);
    R login(String email,String password);
}
