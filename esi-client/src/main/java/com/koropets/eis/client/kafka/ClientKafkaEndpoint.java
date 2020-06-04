package com.koropets.eis.client.kafka;

import com.koropets.eis.client.service.CassandraService;
import com.koropets.eis.common.Sentence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@RequiredArgsConstructor
public class ClientKafkaEndpoint {

    private final CassandraService cassandraService;

    @KafkaListener(topics = {"${kafka.topic.sentence_topic}"})
    public void processSentence(@Payload Sentence sentence) {
        log.info("Got new sentence message {}", sentence.getSentence());
        cassandraService.saveSentence(sentence);
    }
}
