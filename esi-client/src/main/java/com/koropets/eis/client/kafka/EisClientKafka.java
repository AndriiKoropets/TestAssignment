package com.koropets.eis.client.kafka;

import com.koropets.eis.common.Word;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@RequiredArgsConstructor
public class EisClientKafka {

    private final String wordTopic;
    private final KafkaTemplate<String, Word> kafkaTemplate;

    public void sendMessage(final Word msg) {
        log.info("Sending message to kafka topic with word: {}", msg.getWord());
        ListenableFuture<SendResult<String, Word>> future = kafkaTemplate.send(wordTopic, msg);
        setKafkaProducerCallback(msg, future);
    }

    private void setKafkaProducerCallback(Word msg, ListenableFuture<SendResult<String, Word>> future) {
        future.addCallback(new ListenableFutureCallback<SendResult<String, Word>>() {

            @Override
            public void onSuccess(final SendResult<String, Word> message) {
                log.info("Sent message={} with offset={}", message, message.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(final Throwable throwable) {
                log.error("Unable to send message= {}", msg, throwable);
            }
        });
    }
}
