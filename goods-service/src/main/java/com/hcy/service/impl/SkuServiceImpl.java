package com.hcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcy.dto.*;
import com.hcy.entity.Goods;
import com.hcy.entity.Presale;
import com.hcy.entity.Sku;
import com.hcy.mapper.PresaleMapper;
import com.hcy.mapper.SkuMapper;
import com.hcy.pojo.R;
import com.hcy.service.GoodsService;
import com.hcy.service.SkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcy.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
@Transactional
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private PresaleMapper presaleMapper;
    @Autowired
    private GoodsService goodsService;

    @Override
    public List<GoodsForAdmin> findAllSku() {
        List<Sku> allSku = skuMapper.findAllSku();
        List<GoodsForAdmin> goodsForAdmins = new ArrayList<>();
        allSku.forEach(item -> {
            GoodsForAdmin goodsForAdmin = new GoodsForAdmin();
            goodsForAdmin.setGoodsName(item.getGoods().getGoodsName());
            goodsForAdmin.setImgUrl(item.getGoods().getImgUrl());
            SkuForAdmin skuForAdmin = new SkuForAdmin();
            skuForAdmin.setSkuId(item.getSkuId());
            skuForAdmin.setSku(item.getSku());
            skuForAdmin.setSellerName(item.getSeller().getName());
            skuForAdmin.setSellerPhone(item.getSeller().getPhone());
            skuForAdmin.setStock(item.getStock());
            skuForAdmin.setStatus(item.getStatus());
            goodsForAdmin.setSku(skuForAdmin);
            goodsForAdmins.add(goodsForAdmin);
        });
        return goodsForAdmins;
    }

    @Override
    public R modifySkuStatus(int skuId, int status) {
        Sku sku = new Sku();
        sku.setSkuId(skuId);
        sku.setStatus(status);
        this.baseMapper.updateById(sku);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","效果成功！");
        return new R(1000, true, map);
    }

    @Override
    public List<GoodsForSeller> getGoodsSkuBySellerId(String sellerId,Integer currPage,Integer pageSize) {
        System.out.println(sellerId+"----"+currPage+"----"+pageSize);
        // 分页
        IPage<Sku> page = new Page<>();
        page.setCurrent(currPage);
        page.setSize(pageSize);
        IPage<Sku> pageSkus = skuMapper.findSkuBySellerId(sellerId, page);
        List<Sku> skuBySellerId = pageSkus.getRecords();
        System.out.println(skuBySellerId.size());
        List<GoodsForSeller> list = new ArrayList<>();
        skuBySellerId.forEach( item -> {
            GoodsForSeller goodsForSeller = new GoodsForSeller();
            SkuForSeller skuForSeller = new SkuForSeller();
            goodsForSeller.setGoodsId(item.getGoodsId());
            goodsForSeller.setImgUrl(item.getGoods().getImgUrl());
            goodsForSeller.setGoodsName(item.getGoods().getGoodsName());
            goodsForSeller.setOriginalPrice(item.getGoods().getOriginalPrice().setScale(2).toString());
            goodsForSeller.setNowPrice(item.getGoods().getNowPrice().setScale(2).toString());
            goodsForSeller.setBrand(item.getGoods().getBrand());
            skuForSeller.setSkuId(item.getSkuId());
            skuForSeller.setSku(item.getSku());
            skuForSeller.setStock(item.getStock());
            skuForSeller.setStatus(item.getStatus());
            goodsForSeller.setSkuForSeller(skuForSeller);
            list.add(goodsForSeller);
        });
        return list;
    }

    @Override
    public String insertSku(Sku sku) {
        String goodsId = Long.toString(idWorker.nextId());
        sku.setGoodsId(goodsId);
        Goods goods = sku.getGoods();
        goods.setId(goodsId);
        goodsService.insertGoods(goods);
        this.baseMapper.insert(sku);
        return "ok";
    }

    @Override
    public R updateSkuAndGoods(Sku sku) {
        goodsService.updateGoodsById(sku.getGoods());
        this.baseMapper.updateById(sku);
        return new R(1001,true,null);
    }

    @Override
    public R deleteSkuById(Integer skuId) {
        this.baseMapper.deleteById(skuId);
        return new R(1001,true,null);
    }

    @Override
    public Integer countGoodsForSellerId(String sellerId) {
        IPage<Sku> page = new Page<>();
        page.setCurrent(1);
        page.setSize(1);
        IPage<Sku> skuIPage = this.skuMapper.findSkuBySellerId(sellerId, page);
        long total = skuIPage.getTotal();
        return Integer.valueOf((int) total);
    }

    @Override
    public List<GoodsForSearch> searchGoods(String kw,Integer currPage,Integer pageSize) {
        // 分页
        IPage<Sku> page = new Page<>();
        page.setCurrent(currPage);
        page.setSize(pageSize);
        IPage<Sku> pageSkus = skuMapper.searchGoods(kw,page);
        List<GoodsForSearch> searchList = new ArrayList<>();
        pageSkus.getRecords().forEach(item -> {
            GoodsForSearch goodsForSearch = new GoodsForSearch();
            goodsForSearch.setGoodsId(item.getGoodsId());
            goodsForSearch.setGoodsName(item.getGoods().getGoodsName());
            goodsForSearch.setImgUrl(item.getGoods().getImgUrl());
            goodsForSearch.setBrand(item.getGoods().getBrand());
            goodsForSearch.setNowPrice(item.getGoods().getNowPrice());
            System.out.println(item.getGoods().getNowPrice());
            goodsForSearch.setSellerId(item.getSellerId());
            goodsForSearch.setSellerName(item.getSeller().getName());
            searchList.add(goodsForSearch);
        });
        return searchList;
    }

    @Override
    public GoodsForConfirmOrder findSkuOrderConfirm(Integer skuId) {
        Sku skuForConfirmOrder = skuMapper.findSkuForConfirmOrder(skuId);
        GoodsForConfirmOrder goods = new GoodsForConfirmOrder();
        goods.setSku(skuForConfirmOrder);
        String goodsId = skuForConfirmOrder.getGoodsId();
        QueryWrapper<Presale> presaleQueryWrapper = new QueryWrapper<>();
        presaleQueryWrapper.eq("goods_id",goodsId);
        Presale presale = presaleMapper.selectOne(presaleQueryWrapper);
        goods.setPresale(presale);
        return goods;
    }

    @Override
    public Integer findStockBySkuId(Integer skuId) {
        System.out.println("skuId:"+skuId);
        QueryWrapper<Sku> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("stock").eq("sku_id",skuId).eq("status",1);
        Sku sku = this.baseMapper.selectOne(queryWrapper);
        return sku.getStock();
    }

    @Override
    public List<Sku> findSkuByGoodsId(String goodsId) {
        QueryWrapper<Sku> queryWrapper = new QueryWrapper<Sku>();
        queryWrapper.eq("goods_id",goodsId).select("sku_id","stock");
        return this.baseMapper.selectList(queryWrapper);
    }


}
