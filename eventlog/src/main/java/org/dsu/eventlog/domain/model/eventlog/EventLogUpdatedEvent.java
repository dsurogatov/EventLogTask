package org.dsu.eventlog.domain.model.eventlog;

public class EventLogUpdatedEvent extends AbstractEventLogEvent {

    public EventLogUpdatedEvent(EventLogId eventLogId) {
        super(eventLogId);
    }

}
