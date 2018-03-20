package org.dsu.eventlog.domain.model.eventlog;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import org.dsu.eventlog.domain.event.AbstractEvent;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public abstract class AbstractEventLogEvent extends AbstractEvent {

    private final EventLogId eventLogId;

    public AbstractEventLogEvent(EventLogId eventLogId) {
        checkNotNull(eventLogId, argNotBeNull("eventLogId"));
        checkNotNull(eventLogId.id(), argNotBeNull("eventLogId.id"));
        
        this.eventLogId = eventLogId;
    }
}
