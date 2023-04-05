package com.solvd.micro9.synchronizer.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event implements Persistable<String> {

    @Id
    private String id;
    private String name;
    private String description;
    private EventCategory category;
    private LocalDateTime eventTime;
    private BigDecimal price;

    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }

}
