package com.solvd.micro9.synchronizer.service;

import com.solvd.micro9.synchronizer.domain.aggregate.Ticket;
import com.solvd.micro9.synchronizer.domain.eventstore.Es;
import reactor.core.publisher.Mono;

public interface TicketService {

    Mono<Ticket> create(Es eventStore);

    Mono<Ticket> setUserToNull(Es eventStore);

}
