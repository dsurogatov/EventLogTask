package org.dsu.eventlog.domain.model.eventlog;

public final class EventLogCreatedEvent extends AbstractEventLogEvent {

    public EventLogCreatedEvent(EventLogId eventLogId) {
        super(eventLogId);
    }

}
