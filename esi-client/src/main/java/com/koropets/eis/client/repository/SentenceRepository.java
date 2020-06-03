package com.koropets.eis.client.repository;

import com.koropets.eis.client.entity.SentenceTable;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface SentenceRepository extends CassandraRepository<Long, SentenceTable> {
}
