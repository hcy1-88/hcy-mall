package com.hcy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hcy.entity.Presale;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/3 19:31
 */
public interface PresaleService extends IService<Presale> {
    Presale findPresaleByGoodsId(String goodsId);
}
