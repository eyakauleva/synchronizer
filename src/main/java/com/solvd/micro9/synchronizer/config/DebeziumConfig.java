package com.solvd.micro9.synchronizer.config;

import io.debezium.config.Configuration;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;

@org.springframework.context.annotation.Configuration
public class DebeziumConfig {

    @Bean
    public Configuration mongodbConnector() throws IOException {
        File offsetStorageTempFile = File.createTempFile("offsets_", ".dat");
        return Configuration.create()
                .with("name", "tickets-mongodb-connector")
                .with("connector.class", "io.debezium.connector.mongodb.MongoDbConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath())
                .with("offset.flush.interval.ms", "60000")
                .with("mongodb.connection.string", "mongodb://localhost:27017")
                .with("topic.prefix", "tickets-mongodb-connector")
//                .with("mongodb.hosts", "rs0/localhost:27017")
                .with("mongodb.user", "root")
                .with("mongodb.password", "system")
                .with("database.include.list", "tickets")
                .with("capture.mode", "change_streams_update_full_with_pre_image")
                .with("snapshot.delay.ms", "100")
                .with("errors.log.include.messages", "true")
                .build();
    }

}