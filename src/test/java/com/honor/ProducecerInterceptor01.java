package com.honor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * Created by rongyaowen
 * on 2019/9/17.
 */
public class ProducecerInterceptor01 implements ProducerInterceptor {
    /**
     * 发送==> 一条记录调一次和send()同线程
     *
     * @param record
     * @return
     */
    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        System.out.println("拦截器1--发送记录");
        return record;
    }

    /**
     * 发送确认==> 一条记录调一次和doSend()同线程
     *
     * @param metadata
     * @param exception
     */
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        System.out.println("拦截器1--记录回执确认");
    }

    /**
     * 关闭，客户端关闭时调一次，由客户端的close触发
     */
    @Override
    public void close() {
        System.out.println("拦截器1--拦截器关闭");
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
