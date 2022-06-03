package org.nirvana.pulsar.consumer;

import io.github.majusko.pulsar.annotation.PulsarConsumer;
import lombok.extern.slf4j.Slf4j;
import org.nirvana.pulsar.config.PulsarTopic;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author Nirvana
 */
@Slf4j
@Service
public class ByteArrayConsumer {

  /**
   * 具体的topic内容: persistent://public/default/my-topic
   * clazz 写入的是String类型, 报错: Received error from server: Topic does not have schema to check
   * @param message 消息内容
   */
  @PulsarConsumer(topic = PulsarTopic.MY_TOPIC)
  public void consume(byte[] message) {
    String msg = new String(message, StandardCharsets.UTF_8);
    log.info("threadName: {}, Received message: {}", Thread.currentThread().getName(), msg);
  }
}
