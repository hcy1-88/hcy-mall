package com.hcy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hcy.dto.GoodsForAdmin;
import com.hcy.dto.GoodsForConfirmOrder;
import com.hcy.dto.GoodsForSearch;
import com.hcy.dto.GoodsForSeller;
import com.hcy.entity.Sku;
import com.hcy.pojo.R;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hcy
 * @since 2021-10-27
 */
public interface SkuService extends IService<Sku> {
    List<GoodsForAdmin> findAllSku();
    R modifySkuStatus(int skuId,int status);
    List<GoodsForSeller> getGoodsSkuBySellerId(String sellerId,Integer currPage,Integer pageSize);
    String insertSku(Sku sku);

    R updateSkuAndGoods(Sku sku);
    R deleteSkuById(Integer skuId);
    Integer countGoodsForSellerId(String sellerId);
    List<GoodsForSearch> searchGoods(String kw,Integer currPage,Integer pageSize);
    GoodsForConfirmOrder findSkuOrderConfirm(Integer skuId);
    Integer findStockBySkuId(Integer skuId);
    List<Sku> findSkuByGoodsId(String goodsId);
}
