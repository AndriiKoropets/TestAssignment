package com.koropets.eis.client.repository;

import com.koropets.eis.client.entity.SentenceTable;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SentenceRepository extends CassandraRepository<SentenceTable, UUID> {
}
