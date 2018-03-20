package org.dsu.eventlog.domain.command;

import java.time.LocalDateTime;

public interface Command {

    LocalDateTime occuredOn();
}
