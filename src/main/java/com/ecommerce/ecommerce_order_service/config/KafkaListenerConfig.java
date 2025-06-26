package com.ecommerce.ecommerce_order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import com.ecommerce.ecommerce_order_service.model.UserMessage;

@Configuration
public class KafkaListenerConfig {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserMessage> userKafkaListenerFactory(
            ConsumerFactory<String, UserMessage> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, UserMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
