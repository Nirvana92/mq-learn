package org.nirvana.pulsar.consumer;

import io.github.majusko.pulsar.annotation.PulsarConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.nirvana.pulsar.config.PulsarTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutorService;

/**
 * 字符串类型的消费者
 *
 * @author Nirvana
 */
@Service
@Slf4j
public class StringConsumer {
  @Autowired private ExecutorService executorService;

  @PulsarConsumer(
      topic = PulsarTopic.STR_TOPIC,
      clazz = String.class,
      initialPosition = SubscriptionInitialPosition.Earliest)
  public void consume(String message) {
    executorService.execute(
        () -> {
          log.info(
              "threadName: {}, Received message: {}", Thread.currentThread().getName(), message);
        });
  }
}
