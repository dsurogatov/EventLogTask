package org.dsu.eventlog.infrastructura.event;

import org.dsu.eventlog.domain.event.Event;
import org.dsu.eventlog.domain.event.EventListener;
import org.dsu.eventlog.domain.event.EventPublisher;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
class SimpleEventPublisher implements EventPublisher {
    
    private final EventListener eventListener; 

    @Override
    public void publish(Event event) {
        eventListener.eventPerformed(event);
    }

}
