package org.nirvana.pulsar.consumer;

import io.github.majusko.pulsar.annotation.PulsarConsumer;
import lombok.extern.slf4j.Slf4j;
import org.nirvana.pulsar.config.PulsarTopic;
import org.springframework.stereotype.Service;

/**
 * 字符串类型的消费者
 * @author Nirvana
 */
@Service
@Slf4j
public class StringConsumer {

    @PulsarConsumer(topic = PulsarTopic.STR_TOPIC, clazz = String.class)
    public void consume(String message) {
        log.info("threadName: {}, Received message: {}", Thread.currentThread().getName(), message);
    }
}
