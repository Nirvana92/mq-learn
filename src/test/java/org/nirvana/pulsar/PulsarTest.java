package org.nirvana.pulsar;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.shade.org.checkerframework.checker.index.qual.SameLen;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class PulsarTest {

  // 如果有多个brokers, 可以将下面的格式换成 pulsar://localhost:6650,localhost:6651,localhost:6652
  String serviceUrl = "pulsar://localhost:6650";

  PulsarClient client = null;

  @Before
  public void initClient() throws PulsarClientException {
    client = PulsarClient.builder().serviceUrl(serviceUrl).build();
  }

  @Test
  public void testProducer() throws PulsarClientException {
    String topic = "my-topic";
    Producer<byte[]> producer = client.newProducer()
            .topic(topic).create();

    MessageId send = producer.send("hello world".getBytes());
    log.info("send message id: {}", send);
  }

  @Test
  public void testConsumer() throws PulsarClientException {
    String topic = "my-topic";
    String consumerName = "first-consumer";
    Consumer<byte[]> consumer = client.newConsumer().topic(topic)
            .subscriptionName(consumerName)
            .subscribe();
    // 这里是非阻塞的, 只要有一个消息过来程序就会终止
    Message<byte[]> message = consumer.receive();
    log.info("Received message: {}", new String(message.getData()));
  }

  @After
  public void afterClient() throws PulsarClientException {
    client.close();
  }
}
