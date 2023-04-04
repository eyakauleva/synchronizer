package com.solvd.micro9.synchronizer.service.impl;

import com.solvd.micro9.synchronizer.domain.eventstore.Es;
import com.solvd.micro9.synchronizer.service.EventService;
import com.solvd.micro9.synchronizer.service.Synchronizer;
import com.solvd.micro9.synchronizer.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SynchronizerImpl implements Synchronizer {

    private final EventService eventService;
    private final TicketService ticketService;

    @SneakyThrows
    @Override
    public void sync(Es eventStore) {
        switch (eventStore.getType()) {
            case EVENT_CREATED: {
                eventService.create(eventStore).subscribe();
                break;
            }
            case TICKET_CREATED: {
                ticketService.create(eventStore).subscribe();
                break;
            }
            case TICKET_USER_DELETED: {
                ticketService.setUserToNull(eventStore).subscribe();
                break;
            }
        }
    }

}
