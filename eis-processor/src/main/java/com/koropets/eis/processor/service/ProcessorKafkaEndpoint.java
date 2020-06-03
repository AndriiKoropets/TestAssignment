package com.koropets.eis.processor.service;

import com.koropets.eis.common.Word;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@RequiredArgsConstructor
public class ProcessorKafkaEndpoint {

    private final WordProcessor wordProcessor;

    @KafkaListener(topics = {"${kafka.topic.word_topic}"})
    public void processWord(@Payload Word word) {
        log.info("Got new sentence message {}", word.getWord());
        wordProcessor.processWord(word);
    }
}
