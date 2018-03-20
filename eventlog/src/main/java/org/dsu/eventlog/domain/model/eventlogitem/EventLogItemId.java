package org.dsu.eventlog.domain.model.eventlogitem;

import org.dsu.eventlog.domain.model.common.BoxId;

public class EventLogItemId extends BoxId<Long> {

    private EventLogItemId(Long id) {
        super(id);
    }
    
    public static EventLogItemId of(Long id) {
        return new EventLogItemId(id);
    }

}
