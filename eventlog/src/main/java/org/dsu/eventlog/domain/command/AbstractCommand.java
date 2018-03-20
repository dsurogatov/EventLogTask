package org.dsu.eventlog.domain.command;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public abstract class AbstractCommand implements Command {

    @Getter
    private LocalDateTime occuredOn = LocalDateTime.now();

}
