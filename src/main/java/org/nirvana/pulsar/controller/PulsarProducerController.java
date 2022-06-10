package org.nirvana.pulsar.controller;

import io.github.majusko.pulsar.producer.PulsarTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;

/**
 * @author Nirvana
 */
@Slf4j
@RestController
public class PulsarProducerController {

  @Autowired private PulsarTemplate<byte[]> producer;

  @Autowired
  private PulsarTemplate<String> stringProducer;

  @GetMapping("/pulsar/produce/{topic}")
  public String produceMessage(
      @PathVariable("topic") String topic,
      @RequestParam("message") String message,
      @RequestParam("msgType") String msgType)
      throws PulsarClientException {

    MessageId messageId = null;
    if(StringUtils.isBlank(msgType)) {
      // 消息发送的默认类型: byte[]
      messageId = producer.send(topic, message.getBytes());
    }else if (StringUtils.equalsIgnoreCase(msgType, "string")) {
      // 字符串类型的消息
      messageId = stringProducer.send(topic, message);
    }


    return messageId.toString();
  }

  /**
   * todo: 未完成版本, 通过post 提交具体的消息内容
   * @param topic
   * @return
   * @throws PulsarClientException
   */
  @PostMapping("/pulsar/produce/{topic}")
  public String produceMessage(@PathVariable("topic") String topic) throws PulsarClientException {

    return null;
  }
}
