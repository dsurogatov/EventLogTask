package org.dsu.eventlog.infrastructura.event;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.Set;

import org.dsu.eventlog.domain.event.Event;
import org.dsu.eventlog.domain.event.EventHandler;
import org.dsu.eventlog.domain.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
class SimpleEventListener implements EventListener {

    private Set<EventHandler> eventHandlers = new HashSet<>();

    @Override
    public void register(EventHandler eventHandler) {
        checkNotNull(eventHandler, argNotBeNull("eventHandler"));

        eventHandlers.add(eventHandler);
    }

    @Override
    public void eventPerformed(Event event) {
        if (event == null) {
            log.info("The null value of the event was performed.");
            return;
        }

        eventHandlers.stream()
                .filter(handler -> handler.canHandle(event.getClass()))
                .forEach(handler -> handler.handle(event));
    }

}
