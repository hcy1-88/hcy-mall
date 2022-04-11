package com.hcy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hcy.entity.Sku;
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
public interface SkuMapper extends BaseMapper<Sku> {
    List<Sku> findAllSku();
    IPage<Sku> findSkuBySellerId(@Param("sellerId") String sellerId, IPage<Sku> pageSku);
    IPage<Sku> searchGoods(@Param("kw") String kw, IPage<Sku> pageSku);
    List<Sku> findSkuByGoodsId(@Param("goodsId")String goodsId);
    Sku findSkuForConfirmOrder(@Param("skuId") Integer skuId);
}
