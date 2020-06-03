package com.koropets.eis.processor.service;

import com.koropets.eis.common.Sentence;
import com.koropets.eis.common.Word;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@RequiredArgsConstructor
public class WordProcessorImpl implements WordProcessor {

    private final String sentenceTopic;
    private final KafkaTemplate<String, Sentence> kafkaTemplate;

    @Override
    @Async
    public void processWord(Word word) {
        log.info("Processing word: {}", word.getWord());
        Sentence newSentence = Sentence.builder().sentence("Sentence " + word.getWord()).build();
        ListenableFuture<SendResult<String, Sentence>> future = kafkaTemplate.send(sentenceTopic, newSentence);
        setKafkaProducerCallback(newSentence, future);
    }

    private void setKafkaProducerCallback(Sentence sentence, ListenableFuture<SendResult<String, Sentence>> future) {
        future.addCallback(new ListenableFutureCallback<SendResult<String, Sentence>>() {

            @Override
            public void onSuccess(final SendResult<String, Sentence> message) {
                log.info("Sent message={} with offset={}", message, message.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(final Throwable throwable) {
                log.error("Unable to send message= {}", sentence, throwable);
            }
        });
    }
}
