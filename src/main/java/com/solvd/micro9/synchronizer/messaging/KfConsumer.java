package com.solvd.micro9.synchronizer.messaging;

import com.solvd.micro9.synchronizer.domain.eventstore.Es;
import com.solvd.micro9.synchronizer.service.Synchronizer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KfConsumer {

    private final ReactiveKafkaConsumerTemplate<String, Es> receiver;
    private final Synchronizer synchronizer;

    @PostConstruct
    public void fetch() {
        receiver.receive()
                .subscribe(record -> {
                    log.info("received value: {}", record.value());
                    synchronizer.sync(record.value());
                    record.receiverOffset().acknowledge();
                });
    }

}
