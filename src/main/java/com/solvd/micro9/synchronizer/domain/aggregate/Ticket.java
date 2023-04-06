package com.solvd.micro9.synchronizer.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class Ticket implements Persistable<String> {

    @Id
    private String id;

    @Column("user_id")
    private String userId;

    @Column("event_id")
    private String eventId;

    private Integer quantity;

    private BigDecimal price;

    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }

}
