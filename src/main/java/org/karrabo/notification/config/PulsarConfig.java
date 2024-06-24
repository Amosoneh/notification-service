package org.karrabo.notification.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.core.DefaultPulsarClientFactory;
import org.springframework.pulsar.core.PulsarClientFactory;

@Configuration
public class PulsarConfig {

    @Bean
    public PulsarClientFactory pulsarClientFactory() {
        return new DefaultPulsarClientFactory("pulsar://localhost:6650");
    }

    @Bean
    public PulsarClient pulsarClient(PulsarClientFactory pulsarClientFactory) throws PulsarClientException {
        return pulsarClientFactory.createClient();
    }
}
