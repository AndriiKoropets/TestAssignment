package com.koropets.eis.client.service;

import com.koropets.eis.client.kafka.EisClientKafka;
import com.koropets.eis.common.Word;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final EisClientKafka eisClientKafka;

    @Override
    public Word sendMessage(Word word) {
        log.info("Received a message from the user with a word: {}", word.getWord());
        eisClientKafka.sendMessage(word);
        return null;
    }
}