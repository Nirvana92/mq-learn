package org.nirvana.pulsar.config;

import io.github.majusko.pulsar.producer.ProducerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nirvana
 * 配置pulsar 的Producer
 */
@Configuration
public class PulsarConfig {

  @Bean
  public ProducerFactory producerFactory() {
    List<String> allTopics = getAllTopics(PulsarTopic.class);

    ProducerFactory producerFactory = new ProducerFactory();
    for (String topic : allTopics) {
      producerFactory.addProducer(topic);
    }

    return producerFactory;
  }

  /**
   * 通过给定的类对象获取所有的字段
   *
   * @param clazz
   * @return
   */
  public List<String> getAllTopics(Class clazz) {
    List<String> topics = new ArrayList<>();
    Field[] fields = clazz.getFields();
    for (Field field : fields) {
      if (field.getType() == String.class) {
        try {
          topics.add((String) field.get(clazz));
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return topics;
  }
}
