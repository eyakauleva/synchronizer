package com.solvd.micro9.synchronizer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.solvd.micro9.synchronizer.domain.aggregate.Event;
import com.solvd.micro9.synchronizer.domain.aggregate.Ticket;
import com.solvd.micro9.synchronizer.domain.eventstore.Es;
import com.solvd.micro9.synchronizer.persistence.EventRepository;
import com.solvd.micro9.synchronizer.persistence.TicketRepository;
import com.solvd.micro9.synchronizer.service.Synchronizer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class SynchronizerImpl implements Synchronizer {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final Gson gson = new Gson();

    @SneakyThrows
    @Override
    public void sync(Es eventStore) {
        switch (eventStore.getType()) {
            case EVENT_CREATED: {
                ObjectMapper mapper = new ObjectMapper();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                mapper.registerModule(new JavaTimeModule());
                mapper.setDateFormat(dateFormat);
                Event event = mapper.readValue(eventStore.getPayload(), Event.class);
                event.setId(eventStore.getEntityId());
                event.setNew(true);
                eventRepository.save(event).subscribe();
                break;
            }
            case TICKET_CREATED: {
                Ticket ticket = gson.fromJson(eventStore.getPayload(), Ticket.class);
                ticket.setId(eventStore.getEntityId());
                ticket.setNew(true);
                ticketRepository.save(ticket).subscribe();
                break;
            }
            case TICKET_USER_DELETED: {
                ticketRepository.findById(eventStore.getEntityId())
                        .map(ticket -> {
                            ticket.setUserId(null);
                            ticketRepository.save(ticket).subscribe();
                            return ticket;
                        })
                        .subscribe();
                break;
            }
        }
    }

}
