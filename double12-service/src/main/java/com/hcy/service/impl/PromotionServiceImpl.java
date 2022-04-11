package com.hcy.service.impl;

import com.hcy.config.RedisConfig;
import com.hcy.dto.BuyPost;
import com.hcy.dto.GoodsForConfirmOrder;
import com.hcy.entity.PromotionGoods;
import com.hcy.entity.Sku;
import com.hcy.feign.CouponFeign;
import com.hcy.feign.GoodsFeign;
import com.hcy.feign.PromotionGoodsClient;
import com.hcy.pojo.R;
import com.hcy.service.MqService;
import com.hcy.service.PromotionService;
import com.hcy.util.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/23 15:54
 */
@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private GoodsFeign goodsFeign;

    @Autowired
    private PromotionGoodsClient promotionGoodsClient;

    @Autowired
    private CouponFeign couponFeign;

    @Autowired
    private MqService mqService;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private RedissonClient redisson;

    @Override
    @Transactional
    public R buy(BuyPost buyPost) {
        /**
          * 下单：应先通过 分布式锁 操作 redis，减库存
         **/
        String lockKey = "sku_id_" + buyPost.getSkuId();
        Map<String, Object> map = new HashMap<>();
        RLock redissonLock = redisson.getLock(lockKey);
        RedissonRedLock lock = new RedissonRedLock(redissonLock);
        try{
            lock.lock(10, TimeUnit.SECONDS);  // 锁的失效时间
            Integer stock = (Integer) redisTemplate.opsForValue().get(RedisConfig.SKU_STOCK_PREFIX + buyPost.getSkuId());
            if (stock < buyPost.getNum()){
                return new R(2001,false,new HashMap<>());  // 库存不足
            }else {
                redisTemplate.opsForValue().set(RedisConfig.SKU_STOCK_PREFIX + buyPost.getSkuId(),(stock - buyPost.getNum()));
            }
        }finally{
            lock.unlock();
            String orderId = idWorker.nextId() + "";
            buyPost.setOrderId(orderId);
            mqService.sendToStock(buyPost.getSkuId(),buyPost.getNum());  // 异步消息：操作数据库，减库存
            mqService.sendToOrder(buyPost);  // 异步消息：操作数据库，添加订单，状态为 未支付
            map.put("payVo",buyPost);
            return new R(1001,true,map);
        }
    }

    @Override
    public String setStock(Integer skuId) {
        Integer stock = goodsFeign.getStockBySkuId(skuId);
        ValueOperations<String, Object> sop = redisTemplate.opsForValue();
        sop.set(RedisConfig.SKU_STOCK_PREFIX + skuId,stock);
        return "ok，skuId:"+ skuId + "库存是：" + stock + "设置完毕";
    }

    @Override
    public void setStockForAllHotGoods(){
        List<PromotionGoods> all = promotionGoodsClient.getAllPromotionGoodsId();
        all.forEach(h -> {
            List<Sku> skus = goodsFeign.getSkuByGoodsId(h.getGoodsId());
            skus.forEach(sku -> {
                ValueOperations<String, Object> sop = redisTemplate.opsForValue();
                sop.set(RedisConfig.SKU_STOCK_PREFIX + sku.getSkuId(),sku.getStock());
            });
        });
    }

    @Override
    public void setCouponToRedis() {
        couponFeign.getAllCoupon().forEach(coupon -> {
            Duration duration = Duration.between(coupon.getStartTime().toInstant(),coupon.getExpireTime().toInstant());
            redisTemplate.opsForValue().set(RedisConfig.COUPON_IN_REDIS_PREFIX+coupon.getGoodsId(),coupon.getNum(),duration);
        });
    }

    @Override
    public R reduceCoupon(String userId,String goodsId) {
        DefaultRedisScript<String> rs = new DefaultRedisScript<>();  // 这里的泛型是脚本返回值类型
        rs.setScriptSource(new ResourceScriptSource(new ClassPathResource("grab_coupon.lua")));
        rs.setResultType(String.class);
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();

        List<String> keyList = new ArrayList<>();
        keyList.add(RedisConfig.COUPON_IN_REDIS_PREFIX + goodsId);
        keyList.add("CouponBeGrabbed::userid_"+userId+"_goodsId_"+goodsId);
        String s = redisTemplate.execute(rs, stringSerializer,stringSerializer,keyList);
        System.out.println("ss:"+s);
        HashMap<String, Object> hashMap = new HashMap<>();
        if ("a".equals(s)){
            hashMap.put("msg","抢购成功！");
            return new R(1001,true,hashMap);  // 优惠券抢夺成功
        }else if("b".equals(s)){
            hashMap.put("msg","抢购失败，库存不够！");
            return new R(1002,false,hashMap);  // 优惠券库存不够
        }
        hashMap.put("msg","您已经持有了该优惠券，不能重复持有呦！");
        return new R(1003,false,hashMap);
    }

    @Override
    @CachePut(cacheNames = RedisConfig.ORDER_GOODS_PREFIX,key = "'hot_goods_skuid_'+#skuId",condition = "#result != 'null'")
    public GoodsForConfirmOrder findSkuOrderConfirm(Integer skuId) {
        return goodsFeign.findSkuForConfirmOrder(skuId);
    }
}
