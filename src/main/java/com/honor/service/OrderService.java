package com.honor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by rongyaowen
 * on 2019/9/17.
 */
@Service
public class OrderService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "order";

    /**
     * 订单消息写入kafka
     *
     * @param i
     */
    public void orderCommit(int i) {
        kafkaTemplate.send(TOPIC, "order--" + i);
    }

    /**
     * 订单消费者，消费topic为test001的消息
     */
    @KafkaListener(topics = {"order"})
    public void orderConsumer(String msg) {
        System.out.println(msg);
    }
}
