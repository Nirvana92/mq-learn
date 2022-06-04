package org.nirvana.pulsar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Nirvana
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ExecutorService threadPool() {
        return Executors.newFixedThreadPool(10);
    }
}
