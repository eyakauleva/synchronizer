package com.solvd.micro9.synchronizer.persistence;

import com.solvd.micro9.synchronizer.domain.aggregate.Event;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface EventRepository extends R2dbcRepository<Event, String> {
}
