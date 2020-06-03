package com.koropets.eis.client.configuration;

import com.koropets.eis.client.kafka.EisClientKafka;
import com.koropets.eis.client.service.MessageService;
import com.koropets.eis.client.service.MessageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EisClientConfiguration {

    @Bean
    public MessageService messageService(EisClientKafka eisClientKafka) {
        return new MessageServiceImpl(eisClientKafka);
    }
}
