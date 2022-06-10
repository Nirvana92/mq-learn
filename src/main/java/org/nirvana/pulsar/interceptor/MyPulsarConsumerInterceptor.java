package org.nirvana.pulsar.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerInterceptor;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;

import java.util.Set;

@Slf4j
public class MyPulsarConsumerInterceptor implements ConsumerInterceptor {

    public MyPulsarConsumerInterceptor() {
        log.info("MyPulsarConsumerInterceptor()");
    }

    @Override
    public void close() {

    }

    @Override
    public Message beforeConsume(Consumer consumer, Message message) {
        return null;
    }

    @Override
    public void onAcknowledge(Consumer consumer, MessageId messageId, Throwable exception) {

    }

    @Override
    public void onAcknowledgeCumulative(Consumer consumer, MessageId messageId, Throwable exception) {

    }

    @Override
    public void onAckTimeoutSend(Consumer consumer, Set set) {

    }

    @Override
    public void onNegativeAcksSend(Consumer consumer, Set set) {

    }
}
