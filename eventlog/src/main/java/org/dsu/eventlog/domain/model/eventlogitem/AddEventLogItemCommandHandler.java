package org.dsu.eventlog.domain.model.eventlogitem;

import static java.util.Optional.empty;

import java.util.Optional;

import org.dsu.eventlog.domain.command.CommandHandler;
import org.dsu.eventlog.domain.model.common.EntityRepository;
import org.dsu.eventlog.infrastructura.command.DomainCommandHandler;

import lombok.AllArgsConstructor;

@DomainCommandHandler
@AllArgsConstructor
final class AddEventLogItemCommandHandler implements CommandHandler<AddEventLogItemCommand, Void> {

    private final EntityRepository entityRepository;

    @Override
    public Optional<Void> handle(AddEventLogItemCommand command) {
        EventLogItemIntValue eventLogItemIntValue = new EventLogItemIntValue(command.value());
        eventLogItemIntValue.commitToEventLog(command.eventLogId(), command.occuredOn(), entityRepository);
        return empty();
    }

}
