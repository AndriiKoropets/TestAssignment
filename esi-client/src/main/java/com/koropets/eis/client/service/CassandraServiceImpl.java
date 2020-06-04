package com.koropets.eis.client.service;

import com.koropets.eis.client.entity.SentenceTable;
import com.koropets.eis.client.repository.SentenceRepository;
import com.koropets.eis.common.Sentence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CassandraServiceImpl implements CassandraService {

    private final SentenceRepository sentenceRepository;

    @Override
    public void saveSentence(Sentence sentence) {
        log.info("Saving sentence to cassandra. Sentence = {}", sentence.getSentence());
        sentenceRepository.save(SentenceTable.builder().sentence(sentence.getSentence()).build());
        log.info("Saved sentence to cassandra. Sentence = {}", sentence.getSentence());
    }
}
