package com.solvd.micro9.synchronizer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.solvd.micro9.synchronizer.domain.aggregate.Event;
import com.solvd.micro9.synchronizer.domain.aggregate.Ticket;
import com.solvd.micro9.synchronizer.domain.eventstore.Es;
import com.solvd.micro9.synchronizer.persistence.CheckRepo;
import com.solvd.micro9.synchronizer.persistence.EventRepository;
import com.solvd.micro9.synchronizer.persistence.TicketRepository;
import com.solvd.micro9.synchronizer.service.SynchronizerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class SynchronizerServiceImpl implements SynchronizerService {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final Gson gson = new Gson();

    private final CheckRepo checkRepo; //TODO remove

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
            case TICKET_USER_DELETED: { //TODO
                Ticket updatedTicket = new Ticket();
                checkRepo.findById(eventStore.getEntityId())
                        .map(ticket -> {
                            updatedTicket.setId(ticket.getId());
                            updatedTicket.setEventId(ticket.getEventId());
                            updatedTicket.setQuantity(ticket.getQuantity());
                            updatedTicket.setPrice(ticket.getPrice());
                            log.info("in map");
                            log.info("map: " + updatedTicket);
                            return ticket;
                        })
                        //TODO not working (Failed to update table; Row with Id does not exist BUT IT DOES EXIST)
                        .zipWith(ticketRepository.save(updatedTicket))
                        .map(res -> {
                            log.info("res");
                            log.info("- : " + res.getT2());
                            return res.getT2();
                        })
                        .subscribe();

                break;
            }
        }
    }

}
