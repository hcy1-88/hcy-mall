package com.hcy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcy.entity.Presale;
import com.hcy.mapper.PresaleMapper;
import com.hcy.service.PresaleService;
import org.springframework.stereotype.Service;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/3 19:32
 */
@Service
public class PresaleServiceImpl extends ServiceImpl<PresaleMapper, Presale> implements PresaleService {
    @Override
    public Presale findPresaleByGoodsId(String goodsId) {
        return this.baseMapper.selectById(goodsId);
    }
}
