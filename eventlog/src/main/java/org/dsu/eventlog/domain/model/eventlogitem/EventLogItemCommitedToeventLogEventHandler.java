package org.dsu.eventlog.domain.model.eventlogitem;

import org.dsu.eventlog.domain.event.Event;
import org.dsu.eventlog.domain.event.EventHandler;
import org.dsu.eventlog.domain.event.EventListener;
import org.dsu.eventlog.infrastructura.event.DomainEventHandler;

import lombok.extern.slf4j.Slf4j;

@DomainEventHandler
@Slf4j
class EventLogItemCommitedToeventLogEventHandler implements EventHandler {

    EventLogItemCommitedToeventLogEventHandler(EventListener listener) {
        listener.register(this);
    }

    @Override
    public boolean canHandle(Class<? extends Event> clazz) {
        return clazz.isAssignableFrom(EventLogItemCommitedToEventLogEvent.class);
    }

    @Override
    public void handle(Event event) {
        log.info("The EventLogItemCommitedToEventLogEvent's event was handled.");
    }

}
