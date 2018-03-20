package org.dsu.eventlog.domain.event;

public interface EventListener {

    void register(EventHandler eventHandler);
    void eventPerformed(Event event);
}
