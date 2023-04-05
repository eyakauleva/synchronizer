package com.solvd.micro9.synchronizer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.solvd.micro9.synchronizer.domain.aggregate.Event;
import com.solvd.micro9.synchronizer.domain.eventstore.Es;
import com.solvd.micro9.synchronizer.persistence.EventRepository;
import com.solvd.micro9.synchronizer.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @SneakyThrows
    @Override
    public Mono<Event> create(Es eventStore) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(dateFormat);
        Event event = mapper.readValue(eventStore.getPayload(), Event.class);
        event.setId(eventStore.getEntityId());
        event.setNew(true);
        return eventRepository.save(event);
    }

}
