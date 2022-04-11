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
 * Date:  2022/2/26 20:19
 */
@Slf4j
@Service
@RocketMQMessageListener(
        consumerGroup = "goods-service-consumers02",
        topic = "double12-stock",selectorExpression = "double12-stock-rollback",
        consumeMode = ConsumeMode.CONCURRENTLY,
        messageModel = MessageModel.CLUSTERING
)
public class RollbackStockListener implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {
    @Autowired
    private StockService stockService;
    @Override
    public void onMessage(String s) {
        String[] ss = s.split(",");
        int skuId = Integer.valueOf(ss[0].split("=")[1]);
        int num = Integer.valueOf(ss[1].split("=")[1]);
        try {
            stockService.rollbackStock(skuId,num);
        }catch (Exception e){
            log.error("回滚库存失败！skuId："+skuId+",应增加库存："+num+"个");
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
