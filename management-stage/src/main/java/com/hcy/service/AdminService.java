package com.hcy.service;

import com.hcy.entity.Admin;
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
public interface AdminService extends IService<Admin> {
    R addAdmin(Admin admin);
    R login(String email,String password);
    R modifyCheck(int skuId,int status);
    R findAllForCheck();
}
