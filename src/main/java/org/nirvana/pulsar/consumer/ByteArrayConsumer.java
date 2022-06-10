package org.nirvana.pulsar.consumer;

import io.github.majusko.pulsar.annotation.PulsarConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.nirvana.pulsar.config.PulsarTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;

/**
 * @author Nirvana
 */
@Slf4j
@Service
public class ByteArrayConsumer {

  @Autowired private ExecutorService executorService;
  /**
   * 具体的topic内容: persistent://public/default/my-topic clazz 写入的是String类型, 报错: Received error from
   * server: Topic does not have schema to check
   *
   * @param message 消息内容
   */
  @PulsarConsumer(
      topic = PulsarTopic.MY_TOPIC,
      initialPosition = SubscriptionInitialPosition.Earliest)
  public void consume(byte[] message) {
//    executorService.execute(
//        () -> {
//          String msg = new String(message, StandardCharsets.UTF_8);
//          log.info("threadName: {}, Received message: {}", Thread.currentThread().getName(), msg);
//        });

      String msg = new String(message, StandardCharsets.UTF_8);
      log.info("sleep before threadName: {}, Received message: {}", Thread.currentThread().getName(), msg);
      try {
          Thread.sleep(10000);
      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }
      log.info("sleep after threadName: {}, Received message: {}", Thread.currentThread().getName(), msg);
  }
}
