package org.nirvana.pulsar.config;

import io.github.majusko.pulsar.producer.ProducerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.function.Tuples;

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
    ProducerFactory producerFactory = new ProducerFactory();

    // springboot pulsar 的版本为1.1.2 的时候使用这部分代码
    // --------------------------------------------------------------------------------------------------------------------
    List<Pair<String, Class<?>>> allTopics = getAllTopics(PulsarTopic.class);
    for (Pair<String, Class<?>> topicPair : allTopics) {
      producerFactory.addProducer(topicPair.getLeft(), topicPair.getRight());
    }
    // --------------------------------------------------------------------------------------------------------------------

    // springboot pulsar 的版本为1.0.1 的时候使用这部分代码
    // --------------------------------------------------------------------------------------------------------------------
//    producerFactory.addProducer(PulsarTopic.STR_TOPIC, String.class);
    // --------------------------------------------------------------------------------------------------------------------

    return producerFactory;
  }

  /**
   * 通过给定的类对象获取所有的字段
   *
   * @param clazz
   * @return
   */
  public List<Pair<String, Class<?>>> getAllTopics(Class<?> clazz) {
    List<Pair<String, Class<?>>> topics = new ArrayList<>();
    Field[] fields = clazz.getFields();
    for (Field field : fields) {
      String fieldName = field.getName();
      if(fieldName.endsWith(PulsarTopic.CLASS_END_NAME)
              || StringUtils.equalsIgnoreCase(fieldName, "CLASS_END_NAME")) {
        continue;
      }

      try {
        String topicName = (String) field.get(clazz);
        String topicClassFileName = fieldName + PulsarTopic.CLASS_END_NAME;
        Class<?> clazzType = (Class<?>) clazz.getField(topicClassFileName).get(clazz);

        Pair<String, Class<?>> topicPair = Pair.of(topicName, clazzType);
        topics.add(topicPair);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      } catch (NoSuchFieldException e) {
        throw new RuntimeException(e);
      }
    }
    return topics;
  }
}
