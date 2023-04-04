package com.solvd.micro9.synchronizer.service;
//
//import com.mongodb.client.ChangeStreamIterable;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Aggregates;
//import com.mongodb.client.model.Filters;
//import com.solvd.micro9.tickets.persistence.eventstore.DbsSynchronizer;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.bson.Document;
//import org.bson.conversions.Bson;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class MongodbChangesListener { //TODO run in background
//
//    private final DbsSynchronizer synchronizer;
//
//    @Scheduled(fixedDelay = 1000)
//    public void listen() { //TODO (load new library ? )
//
//        log.info("The time is now " + System.currentTimeMillis());
//
//        String uri = "mongodb://root:system@localhost:27017/tickets?authSource=admin";
//        try (MongoClient mongoClient = MongoClients.create(uri)) {
//            MongoDatabase database = mongoClient.getDatabase("tickets");
//            List<Bson> pipeline = Arrays.asList(
//                    Aggregates.match(
//                            Filters.in("operationType",
//                                    Arrays.asList("insert"))));
//
//            ChangeStreamIterable<Document> changeStream = database.watch(pipeline);
//
//            //TODO
//            changeStream.forEach(event -> {
//                System.out.println("Received a change to the collection: " + event);
//                //synchronizer.sync();
//            });
//        }
//    }
//
//}
