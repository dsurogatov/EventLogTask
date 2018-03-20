package org.dsu.eventlog.domain.event;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public abstract class AbstractEvent implements Event {

    @Getter
    private LocalDateTime occuredOn = LocalDateTime.now();

}
