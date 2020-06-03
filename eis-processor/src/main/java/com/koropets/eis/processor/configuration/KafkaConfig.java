package com.koropets.eis.processor.configuration;

import com.koropets.eis.common.Sentence;
import com.koropets.eis.common.Word;
import com.koropets.eis.processor.service.ProcessorKafkaEndpoint;
import com.koropets.eis.processor.service.WordProcessor;
import com.koropets.eis.processor.service.WordProcessorImpl;
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

    @Value("${kafka.topic.sentence_topic}")
    private String sentenceTopic;

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
    public ConsumerFactory<String, Word> consumerFactory() {
        JsonDeserializer<Word> objectJsonDeserializer = new JsonDeserializer<>(Word.class);
        objectJsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), objectJsonDeserializer);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Word>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Word> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ProducerFactory<String, Sentence> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Sentence> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory(), true);
    }

    @Bean
    public WordProcessor wordProcessor(KafkaTemplate<String, Sentence> kafkaTemplate) {
        return new WordProcessorImpl(sentenceTopic, kafkaTemplate);
    }

    @Bean
    public ProcessorKafkaEndpoint processorKafkaEndpoint(WordProcessor wordProcessor) {
        return new ProcessorKafkaEndpoint(wordProcessor);
    }
}
