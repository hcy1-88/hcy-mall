package com.hcy.listener;

import com.hcy.dto.BuyPost;
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
 * Date:  2022/2/26 20:13
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "order-service-consumers02",
        topic = "double12-order",selectorExpression = "double12-order-updateState",
        consumeMode = ConsumeMode.CONCURRENTLY,
        messageModel = MessageModel.CLUSTERING
)
public class UpdateOrderStateListener implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {
    @Autowired
    private OrderService orderService;
    @Override
    public void onMessage(String s) {
        System.out.println("订单更新："+s);
        String[] ss = s.split(",");
        String orderId = ss[0].split("=")[1];
        int state = Integer.valueOf(ss[1].split("=")[1]);
        orderService.updateState(orderId,state);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 每次拉取的间隔，单位为毫秒
        defaultMQPushConsumer.setPullInterval(2000);
        // 设置每次从单个队列中拉取的消息数为16
        defaultMQPushConsumer.setPullBatchSize(16);
    }
}
