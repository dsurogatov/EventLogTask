package org.dsu.eventlog.domain.event;

public interface EventHandler {

    boolean canHandle(Class<? extends Event> clazz);
    void handle(Event event);
}
