package org.dsu.eventlog.domain.model.eventlog;

import static java.util.Optional.empty;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import org.dsu.eventlog.domain.command.CommandHandler;
import org.dsu.eventlog.domain.model.common.EntityRepository;
import org.dsu.eventlog.infrastructura.command.DomainCommandHandler;

import lombok.AllArgsConstructor;

@DomainCommandHandler
@AllArgsConstructor
final class UpdateEventLogCommandHandler implements CommandHandler<UpdateEventLogCommand, Void> {

    private final EntityRepository repository;

    @Override
    public Optional<Void> handle(UpdateEventLogCommand command) {
        checkNotNull(command, argNotBeNull("command"));
        
        EventLog eventLog = repository.findRequired(EventLog.class, command.eventLogId());
        eventLog.update(command.nameDescription(), repository);
        return empty();
    }

}
