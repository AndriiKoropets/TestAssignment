CREATE keyspace eis_cassandra WITH replication = {'class':'SimpleStrategy','replication_factor':1};

use eis_cassandra;

CREATE TABLE sentence (
    uuid varchar,
    sentence varchar,

    PRIMARY KEY(uuid)
);