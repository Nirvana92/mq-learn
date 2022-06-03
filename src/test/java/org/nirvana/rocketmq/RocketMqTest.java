package org.nirvana.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class RocketMqTest {

    static String namesrAddr = "node1:9876";

    @Test
    public void testSync() throws MQClientException, MQBrokerException, RemotingException, InterruptedException, UnsupportedEncodingException {
        DefaultMQProducer producer = new DefaultMQProducer("simple_sync_group_name");
        // producer.setVipChannelEnabled(false);
        producer.setNamesrvAddr(namesrAddr);
        producer.start();

        String topicName = "simple_sync_topic";
        Message msg = new Message(topicName, "hello word".getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult result = producer.send(msg);
        System.out.println("result: " + result);

        producer.shutdown();
    }

    @Test
    public void testAsync() throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("simple_sync_producer_group_name");
        producer.setNamesrvAddr(namesrAddr);
        producer.start();

        producer.send(new Message("topic", "word".getBytes(RemotingHelper.DEFAULT_CHARSET)), new SendCallback() {
            public void onSuccess(SendResult sendResult) {
                System.out.println("success: " + sendResult);
            }

            public void onException(Throwable throwable) {
                System.out.println("error: " + throwable);
            }
        });

        producer.shutdown();
    }
}
