package com.solvd.micro9.synchronizer.persistence;

import com.solvd.micro9.synchronizer.domain.aggregate.Ticket;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;


//TODO remove !

@Repository
public interface CheckRepo extends R2dbcRepository<Ticket, String> {
}
