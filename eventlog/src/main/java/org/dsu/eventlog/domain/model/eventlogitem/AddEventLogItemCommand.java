package org.dsu.eventlog.domain.model.eventlogitem;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import org.dsu.eventlog.domain.command.AbstractCommand;
import org.dsu.eventlog.domain.model.eventlog.EventLogId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter(AccessLevel.PRIVATE)
public final class AddEventLogItemCommand extends AbstractCommand {
    
    private EventLogId eventLogId;
    private int value;

    public AddEventLogItemCommand(EventLogId eventLogId, int value) {
        checkNotNull(eventLogId, argNotBeNull("eventLogId"));
        
        this.eventLogId = eventLogId;
        this.value = value;
    }

}
