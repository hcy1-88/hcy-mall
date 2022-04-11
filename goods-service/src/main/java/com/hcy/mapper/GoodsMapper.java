package com.hcy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcy.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hcy
 * @since 2021-10-27
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    List<Goods> getAllGoods();
    String findImgUrlByGoodsId(@Param("gid") String gid);

}
