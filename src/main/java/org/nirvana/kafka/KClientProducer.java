package org.nirvana.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author gzm
 * @date 2021/3/12 5:39 下午
 * @desc: kafak 生产者
 */
public class KClientProducer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        String topicName = "test-topic";

        Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>(topicName, "msg-key", "msg-value"));
        System.out.println(future.get());
        producer.close();
    }
}
