package com.hcy.service.impl;

import com.alibaba.fastjson.JSON;
import com.hcy.dto.BuyPost;
import com.hcy.service.MqService;
import com.hcy.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/24 20:06
 */
@Slf4j
@Service
public class MqServiceImpl implements MqService {
    @Autowired
    private RocketMQTemplate mqTemplate;

    /*
    topics:
      - double12-order
      - reduce-stock
    tags:
      - double12-order-tag
      - reduce-stock-tag
    * */
    @Value("${topics}")
    private List<String> topics;

    @Value("${tags}")
    private List<String> tags;

    @Override
    public void sendToStock(Integer skuId,Integer num) {
        String topic = topics.get(1);  // 库存
        String tag = tags.get(1);  // 扣减
        String msg = "skuId="+skuId+",num="+num;  // skuId=12,num=30
        mqTemplate.asyncSend(topic + ":" + tag, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {
                log.error("消息【扣减库存】发送异常："+msg+"---\n"+ExceptionUtil.exceptionToString(throwable));
            }
        });
    }

    @Override
    public void sendToOrder(BuyPost buyPost) {
        String topic = topics.get(0);  // 插入订单
        String tag = tags.get(0);
        mqTemplate.asyncSend(topic + ":" + tag, buyPost, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {
                log.error("消息【插入订单】发送异常："+buyPost+"---\n"+ExceptionUtil.exceptionToString(throwable));
            }
        });
    }

    @Override
    public void updateOrderState(String orderId, Integer state) {
        String topic = topics.get(0);  // 订单状态的更新
        String tag = tags.get(2);
        String msg = "orderId="+orderId+",state="+state;
        mqTemplate.asyncSend(topic + ":" + tag, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {
                log.error("消息【更新订单状态】发送异常："+msg+"---\n"+ExceptionUtil.exceptionToString(throwable));
            }
        });
    }

    @Override
    public void rollBackStock(Integer skuId, Integer num) {
        String topic = topics.get(1);  // 库存topic
        String tag = tags.get(3);  // 回滚库存
        String msg = "skuId="+skuId+",num="+num;
        mqTemplate.asyncSend(topic + ":" + tag, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {
                log.error("消息【扣减库存】发送异常："+msg+"---\n"+ExceptionUtil.exceptionToString(throwable));
            }
        });
    }
}
