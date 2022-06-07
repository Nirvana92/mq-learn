package org.nirvana.pulsar;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nirvana.pulsar.config.PulsarConfig;
import org.nirvana.pulsar.config.PulsarTopic;
import org.nirvana.pulsar.message.MyMsg;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class PulsarTest {

  // 如果有多个brokers, 可以将下面的格式换成 pulsar://localhost:6650,localhost:6651,localhost:6652
  String serviceUrl = "pulsar://localhost:6650";

  PulsarClient client = null;

  Producer<byte[]> producer = null;

  Producer<MyMsg> myMsgProducer = null;

  Consumer<byte[]> consumer = null;

  ExecutorService executor = Executors.newFixedThreadPool(10);

  @Before
  public void initClient() throws PulsarClientException {
    client = PulsarClient.builder().serviceUrl(serviceUrl).build();
  }

  /**
   * 同步发送消息
   *
   * @throws PulsarClientException
   */
  @Test
  public void testProducerSync() throws PulsarClientException {
    String topic = "my-topic";
    producer = client.newProducer().topic(topic).create();

    MessageId send = producer.send("hello world".getBytes());
    log.info("send message id: {}", send);
  }

  /** 异步发送消息 */
  @Test
  public void testProducerAsync()
      throws PulsarClientException, ExecutionException, InterruptedException {
    String topic = "my-topic-async";
    producer = client.newProducer().topic(topic).create();

    CompletableFuture<MessageId> sendFuture = producer.sendAsync("send-message-async".getBytes());
    MessageId messageId = sendFuture.get();
    log.info("send message id: {}", messageId);
  }

  /**
   * 通过schema 的方式发送消息
   *
   * @throws PulsarClientException
   */
  @Test
  public void testProducerSchema() throws PulsarClientException {
    String topic = "my-topic-schema";
    myMsgProducer = client.newProducer(Schema.AVRO(MyMsg.class)).topic(topic).create();

    MyMsg myMsg = MyMsg.builder().message("message-schema").build();
    MessageId messageId = myMsgProducer.send(myMsg);
    log.info("send message id: {}", messageId);
  }

  /**
   * 同步消费消息
   *
   * @throws PulsarClientException
   */
  @Test
  public void testConsumerSync() throws PulsarClientException {
    String topic = "my-topic";
    String consumerName = "first-consumer";
    consumer = client.newConsumer().topic(topic).subscriptionName(consumerName).subscribe();
    // 这里是非阻塞的, 只要有一个消息过来程序就会终止
    Message<byte[]> message = consumer.receive();
    log.info("Received message: {}", new String(message.getData()));
  }

  /** 异步消费消息 */
  @Test
  public void testConsumerAsync()
      throws PulsarClientException, ExecutionException, InterruptedException {
    String topic = "my-topic-async";
    consumer = client.newConsumer().topic(topic).subscriptionName("consumer-async").subscribe();
    CompletableFuture<Message<byte[]>> future = consumer.receiveAsync();
    Message<byte[]> message = future.get();
    log.info("Received message: {}", new String(message.getData()));

    // 异步处理逻辑消费消息
    executor.execute(
        () -> {
          Message<byte[]> msg = null;
          try {
            msg = consumer.receive();
            // 业务逻辑处理
            log.info("Received message: {}", new String(msg.getData()));
            consumer.acknowledge(msg);

          } catch (PulsarClientException e) {
            consumer.negativeAcknowledge(msg);
            throw new RuntimeException(e);
          }
        });

    // ack 消息
    //    consumer.acknowledge(message);
    //    consumer.negativeAcknowledge(message);
  }

  @Test
  public void testClassField() throws IllegalAccessException {
    Field[] fields = PulsarTopic.class.getFields();
    for (Field field : fields) {
      log.info("field name: {}", field.getName());
      Object val = field.get(PulsarTopic.class);
      log.info("field value: {}", val);
    }
  }

  @Test
  public void testGetAllTopics() {
    PulsarConfig pulsarConfig = new PulsarConfig();
    List<Pair<String, Class<?>>> allTopics = pulsarConfig.getAllTopics(PulsarTopic.class);
    System.out.println(allTopics);
  }

  @After
  public void afterClient() throws PulsarClientException {
    producer.close();
    consumer.close();
    client.close();
  }
}
