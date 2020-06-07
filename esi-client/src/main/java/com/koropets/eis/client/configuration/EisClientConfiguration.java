package com.koropets.eis.client.configuration;

import com.koropets.eis.client.kafka.EisClientKafka;
import com.koropets.eis.client.repository.SentenceRepository;
import com.koropets.eis.client.service.CassandraService;
import com.koropets.eis.client.service.CassandraServiceImpl;
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

    @Bean
    public CassandraService cassandraService(SentenceRepository sentenceRepository) {
        return new CassandraServiceImpl(sentenceRepository);
    }
}
