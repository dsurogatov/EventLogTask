package org.dsu.eventlog.domain.command;

import java.util.Optional;

public interface CommandHandler<C, R> {

    Optional<R> handle(C command);
}
