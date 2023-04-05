package com.solvd.micro9.synchronizer.service.impl;

import com.google.gson.Gson;
import com.solvd.micro9.synchronizer.domain.aggregate.Ticket;
import com.solvd.micro9.synchronizer.domain.eventstore.Es;
import com.solvd.micro9.synchronizer.persistence.TicketRepository;
import com.solvd.micro9.synchronizer.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public Mono<Ticket> create(Es eventStore) {
        Ticket ticket = new Gson().fromJson(eventStore.getPayload(), Ticket.class);
        ticket.setId(eventStore.getEntityId());
        ticket.setNew(true);
        return ticketRepository.save(ticket);
    }

    @Override
    public Mono<Ticket> setUserToNull(Es eventStore) {
        return ticketRepository.findById(eventStore.getEntityId())
                .map(ticket -> {
                    ticket.setUserId(null);
                    ticketRepository.save(ticket).subscribe();
                    return ticket;
                });
    }

}
