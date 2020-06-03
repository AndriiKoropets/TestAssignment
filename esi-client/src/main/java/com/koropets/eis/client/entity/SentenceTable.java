package com.koropets.eis.client.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SentenceTable {

    @PrimaryKey
    private final String id;
    private final String sentence;
}
