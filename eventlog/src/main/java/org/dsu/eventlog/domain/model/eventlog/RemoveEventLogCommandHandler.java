package org.dsu.eventlog.domain.model.eventlog;

import static java.util.Optional.empty;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import org.dsu.eventlog.domain.command.CommandHandler;
import org.dsu.eventlog.domain.model.common.EntityRepository;
import org.dsu.eventlog.domain.model.eventlogitem.EventLogItemRepository;
import org.dsu.eventlog.infrastructura.command.DomainCommandHandler;

import lombok.AllArgsConstructor;

@DomainCommandHandler
@AllArgsConstructor
final class RemoveEventLogCommandHandler implements CommandHandler<RemoveEventLogCommand, Void> {

    private final EntityRepository entityRepository;
    private final EventLogItemRepository eventLogItemRepository;

    @Override
    public Optional<Void> handle(RemoveEventLogCommand command) {
        checkNotNull(command, argNotBeNull("command"));
        
        EventLog eventLog = entityRepository.findRequired(EventLog.class, command.eventLogId());
        eventLogItemRepository.deleteByEventLogId(eventLog.id());
        eventLog.remove(entityRepository);
        return empty();
    }

}
