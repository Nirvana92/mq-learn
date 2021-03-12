package org.nirvana.rocketmq.simple.sync;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author gzm
 * @date 2021/3/12 10:42 上午
 * @desc: 同步的生产者
 */
public class SyncProducer {
    static String namesrAddr = "localhost:9876";

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
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
}
