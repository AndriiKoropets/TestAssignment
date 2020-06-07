package com.koropets.eis.client.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("sentence")
@Data
@Builder
public class SentenceTable {

    @PrimaryKey
    @Column
    private final String uuid;
    @Column
    private final String sentence;
}
