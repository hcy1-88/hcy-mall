package com.hcy.listener;

import com.hcy.service.StockService;
import com.hcy.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
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
 * Date:  2022/2/24 21:01
 */
@Slf4j
@Service
@RocketMQMessageListener(
        consumerGroup = "goods-service-consumers01",
        topic = "double12-stock",selectorExpression = "double12-stock-reduce",
        consumeMode = ConsumeMode.CONCURRENTLY,
        messageModel = MessageModel.CLUSTERING
)
public class ReduceStockListener implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {
    @Autowired
    private StockService stockService;

    @Override
    public void onMessage(String msg) {
        System.out.println("消息："+ msg);  // 形式如  skuId=12,num=30
        String[] kv = msg.split(",");
        Integer skuId = Integer.valueOf(kv[0].split("=")[1]);
        Integer num = Integer.valueOf(kv[1].split("=")[1]);
        try {
            stockService.reduceStock(skuId,num);
        }catch (Exception e){
            log.error("扣减库存失败，扣减信息："+msg+"---\n"+ ExceptionUtil.exceptionToString(e));
        }

    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 每次拉取的间隔，单位为毫秒
        defaultMQPushConsumer.setPullInterval(2000);
        // 设置每次从单个队列中拉取的消息数为16
        defaultMQPushConsumer.setPullBatchSize(16);
    }
}
