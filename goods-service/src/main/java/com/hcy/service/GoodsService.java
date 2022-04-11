package com.hcy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hcy.dto.GoodsForDetail;
import com.hcy.entity.Goods;
import com.hcy.pojo.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hcy
 * @since 2021-10-27
 */
public interface GoodsService extends IService<Goods> {
    List<Goods> getAllGoods();
    String insertGoods(Goods goods);
    R updateGoodsById(Goods goods);
    R deleteGoodsById(String goodsId);
    String updateGoodsImg(MultipartFile mf,String goodsId);
    List<GoodsForDetail> findDetailByGoodsId(String goodsId);
}
