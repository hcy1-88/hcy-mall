package com.hcy.listener;

import com.hcy.dto.BuyPost;
import com.hcy.entity.Order;
import com.hcy.service.OrderService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/24 20:44
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "order-service-consumers01",
        topic = "double12-order",selectorExpression = "double12-order-add",
        consumeMode = ConsumeMode.CONCURRENTLY,
        messageModel = MessageModel.CLUSTERING
)
public class AddOrderListener implements RocketMQListener<BuyPost>, RocketMQPushConsumerLifecycleListener {
    @Autowired
    private OrderService orderService;

    @Override
    public void onMessage(BuyPost buyPost) {
        System.out.println(buyPost);
        orderService.placeOrder(buyPost);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 每次拉取的间隔，单位为毫秒
        defaultMQPushConsumer.setPullInterval(2000);
        // 设置每次从单个队列中拉取的消息数为16
        defaultMQPushConsumer.setPullBatchSize(16);
    }
}
