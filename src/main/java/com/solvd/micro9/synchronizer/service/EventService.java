package com.solvd.micro9.synchronizer.service;

import com.solvd.micro9.synchronizer.domain.aggregate.Event;
import com.solvd.micro9.synchronizer.domain.eventstore.Es;
import reactor.core.publisher.Mono;

public interface EventService {

    Mono<Event> create(Es eventStore);

}
