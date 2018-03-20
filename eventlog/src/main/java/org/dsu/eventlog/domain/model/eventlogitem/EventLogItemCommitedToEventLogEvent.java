package org.dsu.eventlog.domain.model.eventlogitem;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import org.dsu.eventlog.domain.event.AbstractEvent;
import org.dsu.eventlog.domain.model.eventlog.EventLogId;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public final class EventLogItemCommitedToEventLogEvent extends AbstractEvent {

    private final EventLogId eventLogId;
    private final EventLogItemId eventLogItemId;

    public EventLogItemCommitedToEventLogEvent(EventLogId eventLogId, EventLogItemId eventLogItemId) {
        checkNotNull(eventLogId, argNotBeNull("eventLogId"));
        checkNotNull(eventLogId.id(), argNotBeNull("eventLogId.id"));
        checkNotNull(eventLogItemId, argNotBeNull("eventLogItemId"));
        checkNotNull(eventLogItemId.id(), argNotBeNull("eventLogItemId.id"));
        
        this.eventLogId = eventLogId;
        this.eventLogItemId = eventLogItemId;
    }
}
