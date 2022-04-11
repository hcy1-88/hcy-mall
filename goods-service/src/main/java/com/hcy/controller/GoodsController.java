package com.hcy.controller;

import com.hcy.dto.GoodsForDetail;
import com.hcy.entity.Goods;
import com.hcy.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hcy
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @RequestMapping("/getAllGoods")
    public List<Goods> getAll(){
        return goodsService.getAllGoods();
    }

    @RequestMapping("/updateImg")
    public String updateImg(MultipartFile file, String goodsId) {
        // 添加商品的图片
        return goodsService.updateGoodsImg(file,goodsId);
    }

    @RequestMapping("/getDetailByGoodsId")
    public List<GoodsForDetail> getDetailByGoodsId(String goodsId) {
        return goodsService.findDetailByGoodsId(goodsId);
    }


}

