package com.koropets.eis.client.service;

import com.koropets.eis.common.Sentence;

public interface CassandraService {
    void saveSentence(Sentence sentence);
}
