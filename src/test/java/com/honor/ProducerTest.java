package com.honor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by rongyaowen
 * on 2019/9/12.
 */
public class ProducerTest {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "ubuntu0020:9092");
        // ack是判别请求是否为完整的条件（就是是判断是不是成功发送了）。
        // 我们指定了“all”将会阻塞消息，这种设置性能最低，但是是最可靠的。
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        // 如果请求失败，生产者会自动重试，我们指定是0次，如果启用重试，则会有重复消息的可能性。
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        // (生产者)缓存每个分区未发送的消息
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 生产者发送请求等待时间
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // 控制生产者可用的缓存总量
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        //将用户提供的key和value对象ProducerRecord转换成字节
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        // 配置自定义分区器
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class);
        // 配置自定义拦截器
        List<Class> interceptorList = new ArrayList<>();
        interceptorList.add(ProducecerInterceptor01.class);
        interceptorList.add(ProducecerInterceptor02.class);
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptorList);
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++)
            producer.send(new ProducerRecord<>("first", Integer.toString(i), Integer.toString(i)), (metadata, e) -> {
                if (e != null)
                    e.printStackTrace();
                System.out.println("The offset of the record we just sent is: " + metadata.offset() + " -- The partition is " + metadata.partition());
            });

        // 关闭客户端功能
        producer.close();
    }
}
