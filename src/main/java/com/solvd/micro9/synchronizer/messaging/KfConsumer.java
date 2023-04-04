package com.solvd.micro9.synchronizer.messaging;

import com.solvd.micro9.synchronizer.domain.eventstore.Es;
import com.solvd.micro9.synchronizer.service.SynchronizerService;
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
    private final SynchronizerService synchronizerService;

    @PostConstruct
    public void fetch() {
        receiver.receive()
                .subscribe(record -> {
                    log.info("received message: {}", record);
                    synchronizerService.sync(record.value());
                    record.receiverOffset().acknowledge();
                });
    }

}
