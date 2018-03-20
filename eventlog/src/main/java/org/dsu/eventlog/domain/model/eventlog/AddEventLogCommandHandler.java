package org.dsu.eventlog.domain.model.eventlog;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import org.dsu.eventlog.domain.command.CommandHandler;
import org.dsu.eventlog.domain.model.common.EntityRepository;
import org.dsu.eventlog.infrastructura.command.DomainCommandHandler;

import lombok.AllArgsConstructor;

@DomainCommandHandler
@AllArgsConstructor
final class AddEventLogCommandHandler implements CommandHandler<AddEventLogCommand, EventLogId> {

    private final EntityRepository entityRepository;

    @Override
    public Optional<EventLogId> handle(AddEventLogCommand command) {
        checkNotNull(command, argNotBeNull("command"));

        EventLog eventLog = new EventLog(command.name(), command.description());
        entityRepository.add(eventLog);
        return Optional.of(eventLog.eventLogId());
    }

}
