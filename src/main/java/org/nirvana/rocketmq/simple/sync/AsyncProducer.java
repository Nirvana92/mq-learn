package org.nirvana.rocketmq.simple.sync;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author gzm
 * @date 2021/3/12 10:47 上午
 * @desc: 异步发送消息的处理方案
 */
public class AsyncProducer {
    static String namesrAddr = "";

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
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
