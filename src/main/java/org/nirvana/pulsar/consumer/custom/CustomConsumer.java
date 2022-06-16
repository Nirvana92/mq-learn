package org.nirvana.pulsar.consumer.custom;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class CustomConsumer {

    @Autowired
    private PulsarClient pulsarClient;

    @Autowired
    private ExecutorService executorService;

    public void subscribe() throws PulsarClientException {
        pulsarClient.newConsumer()
                .topic("my-topic")
                .subscriptionName("my-custom-subscription")
                .messageListener((consumer, msg) -> {
                    executorService.execute(() -> {
                        log.info("threadName: {}, Received message: {}", Thread.currentThread().getName(), new String(msg.getData()));
                        try {
                            consumer.acknowledge(msg);
                        } catch (PulsarClientException e) {
                            throw new RuntimeException(e);
                        }
                    });
                })
                .subscribe();


    }
}
