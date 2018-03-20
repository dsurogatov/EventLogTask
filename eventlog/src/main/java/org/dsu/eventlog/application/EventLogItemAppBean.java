package org.dsu.eventlog.application;

import org.dsu.eventlog.domain.model.eventlog.EventLogId;
import org.dsu.eventlog.domain.model.eventlogitem.AddEventLogItemCommand;
import org.dsu.eventlog.infrastructura.command.CommandExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Component
@Transactional
@AllArgsConstructor
public class EventLogItemAppBean {

    private final CommandExecutor commandExecutor;
    
    public void addIntValue(long eventLogId, int value) {
        commandExecutor.execute(new AddEventLogItemCommand(EventLogId.of(eventLogId), value));
    }
}
