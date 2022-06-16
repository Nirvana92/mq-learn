package org.nirvana;

import lombok.extern.slf4j.Slf4j;
import org.nirvana.pulsar.consumer.custom.CustomConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class MqApplication implements CommandLineRunner {
    public static void main(String[] args) {

        SpringApplication.run(MqApplication.class, args);
    }

    @Autowired
    private CustomConsumer customConsumer;

    @Override
    public void run(String... args) throws Exception {
        customConsumer.subscribe();
        log.info("customConsumer.subscribe()");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("==================== ApplicationReadyEvent");
    }

}
