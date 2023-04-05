package com.solvd.micro9.synchronizer.persistence;

import com.solvd.micro9.synchronizer.domain.aggregate.Ticket;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TicketRepository extends R2dbcRepository<Ticket, String> {
}
