package org.dsu.eventlog.domain.event;

import java.time.LocalDateTime;

public interface Event {

    LocalDateTime occuredOn();
}
