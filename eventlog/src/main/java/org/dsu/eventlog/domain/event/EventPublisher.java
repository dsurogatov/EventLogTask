package org.dsu.eventlog.domain.event;

public interface EventPublisher {

    void publish(Event event);
}
