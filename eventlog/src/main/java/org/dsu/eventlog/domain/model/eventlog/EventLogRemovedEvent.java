package org.dsu.eventlog.domain.model.eventlog;

public class EventLogRemovedEvent extends AbstractEventLogEvent {

    public EventLogRemovedEvent(EventLogId eventLogId) {
        super(eventLogId);
    }
}
