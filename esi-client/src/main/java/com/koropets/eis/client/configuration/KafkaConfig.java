package com.koropets.eis.client.configuration;

import com.koropets.eis.client.kafka.ClientKafkaEndpoint;
import com.koropets.eis.client.kafka.EisClientKafka;
import com.koropets.eis.client.service.CassandraService;
import com.koropets.eis.common.Sentence;
import com.koropets.eis.common.Word;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${kafka.servers}")
    private String bootstrapServer;

    @Value("${kafka.topic.word_topic}")
    private String wordTopic;

    @Value("${kafka.groupId}")
    private String groupId;

    @Value("${kafka.consumer.offsetConfig}")
    private String autoOffsetReset;

    @Bean
    public Map<String, Object> producerConfigs(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        return props;
    }

    @Bean
    public ProducerFactory<String, Word> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public ConsumerFactory<String, Sentence> consumerFactory() {
        JsonDeserializer<Sentence> objectJsonDeserializer = new JsonDeserializer<>(Sentence.class);
        objectJsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), objectJsonDeserializer);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Sentence>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Sentence> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public KafkaTemplate<String, Word> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory(), true);
    }

    @Bean
    public EisClientKafka eisClientKafka(KafkaTemplate<String, Word> kafkaTemplate) {
        return new EisClientKafka(wordTopic, kafkaTemplate);
    }

    @Bean
    public ClientKafkaEndpoint clientKafkaEndpoint(CassandraService cassandraService) {
        return new ClientKafkaEndpoint(cassandraService);
    }
}
