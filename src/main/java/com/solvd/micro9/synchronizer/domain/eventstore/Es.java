package com.solvd.micro9.synchronizer.domain.eventstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Es {

    private Long id;
    private EsEventType type;
    private LocalDateTime time;
    private String createdBy;
    private String entityId;
    private String payload;
    private EsStatus status;

}
