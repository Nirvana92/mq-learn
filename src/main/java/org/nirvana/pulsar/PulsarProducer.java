package org.nirvana.pulsar;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PulsarProducer {
    String pulsarUrl = "pulsar://localhost:6650";
    String topicName = "";

    PulsarClient client = null;
    @Before
    public void init() throws PulsarClientException {
        client = PulsarClient.builder()
                .serviceUrl(pulsarUrl)
                .build();
    }

    @Test
    public void producer() throws PulsarClientException {
        Producer<byte[]> producer = client.newProducer()
                .topic("my-topic")
                .create();

        // 然后你就可以发送消息到指定的broker 和topic上：
        producer.send("My message".getBytes());

        producer.close();
    }

    @Test
    public void consumer() throws PulsarClientException {
        Consumer consumer = client.newConsumer()
                .topic("my-topic")
                .subscriptionName("my-subscription")
                .subscribe();


    }

    @After
    public void close() throws PulsarClientException {
        client.close();
    }

}
