package com.hcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hcy.dto.BuyPost;
import com.hcy.dto.OrderForUser;
import com.hcy.entity.Goods;
import com.hcy.entity.Order;
import com.hcy.entity.OrderState;
import com.hcy.entity.Presale;
import com.hcy.feign.PresaleClient;
import com.hcy.mapper.OrderMapper;
import com.hcy.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcy.util.ExceptionUtil;
import com.hcy.util.IdWorker;
import com.hcy.util.OrderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hcy
 * @since 2022-02-10
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PresaleClient presaleClient;
    @Override
    public void placeOrder(BuyPost buyPost) {
        Order order = OrderUtil.buyPostToOrder(buyPost);
        order.setState(OrderState.NO_PAID);  // 尚未支付
        // 放在消息幂等性 而重复插入订单报错
        QueryWrapper<Order> eq = new QueryWrapper<Order>().select("id").eq("id", order.getId());
        Order order1 = this.baseMapper.selectOne(eq);
        if (order1 != null){
            return;
        }
        try {
            int x = this.baseMapper.insert(order);
        }catch (Exception e){
            log.error("订单插入数据库失败，订单信息："+order+"---\n"+ ExceptionUtil.exceptionToString(e));
        }

    }

    @Override
    public void updateState(String orderId, Integer state) {
        // 支付后，回执行这个方法
        Order order = new Order();
        order.setId(orderId);
        order.setTradeNo(UUID.randomUUID().toString());
        order.setState(state);  // 状态由前端来指定方便些
        try {
            this.baseMapper.updateById(order);
        }catch (Exception e){
            log.error("数据库订单状态更新失败，订单id为："+orderId+",状态："+state+"---\n"+ExceptionUtil.exceptionToString(e));
        }
    }

    @Override
    public List<OrderForUser> findOrderForUserByUid(String uid) {
        List<OrderForUser> orders = orderMapper.findOrderByUid(uid);
        if (orders == null || orders.size() == 0) {
            return orders;
        }
        orders.forEach(order -> {
            // 查看是不是预售商品
            // 只支付了一部分
            Presale presale = presaleClient.getPresaleByGoodsId(order.getGoodsId());
            if (presale != null){
                order.setPresale(true);
            }
            if (order.getTradeState() == OrderState.PAID_ONLY_PART){
                // 是预售商品，则 计算 remainMoney 看还有多少钱没付
                String nowPrice = order.getUnitPrice();  // 这个是现价单价 now Price
                BigDecimal a = new BigDecimal(nowPrice);
                // 尾款 = 现价 * 购买数量 - factMoney
                String remain = a.multiply(new BigDecimal(order.getNum())).subtract(new BigDecimal(order.getFactMoney())).toString();
                order.setRemainToPay(remain);
                order.setTradeState(OrderForUser.ONLY_PAID_PART);
            }else if(order.getTradeState() == OrderState.NO_PAID){
                // 还没付
                order.setRemainToPay(order.getFactMoney());
                order.setTradeState(OrderForUser.NO_PAID_NO_DELIVER);
                order.setFactMoney("0");
            }else if(order.getTradeState() == OrderState.HAVE_PAID_ALL){
                // 已经完全支付了
                order.setRemainToPay("0");
                if (order.getExpressId() == null || "".equals(order.getExpressId())){
                    // 没有 expressId， 说明未发货
                    order.setTradeState(OrderForUser.PAID_NO_DELIVER);  // 1
                }else{
                    // 否则，如果有 expressId，说明有物流信息，买卖家已经发货
                    // 此时，应查询物流系统，看用户是否已经收货
                    // 为了方便，物流系统不是重点，咱们就默认 用户没收货
                    order.setTradeState(OrderForUser.PAID_DELIVERED_NO_RECEIVE);  // 3
                }
            }else if(order.getTradeState() == OrderState.FAILED_PAY){
                // 支付失败，尚未支付
                order.setRemainToPay(order.getFactMoney());
                order.setTradeState(OrderForUser.NO_PAID_NO_DELIVER);
                order.setFactMoney("0");
            }
        });
        return orders;
    }

    // 付尾款
    @Override
    public void payTailMoney(String orderId, String factMoney, Integer state) {
        Order order = new Order();
        order.setId(orderId);
        order.setPrice(new BigDecimal(factMoney));
        order.setState(state);
        this.baseMapper.updateById(order);
    }
}
