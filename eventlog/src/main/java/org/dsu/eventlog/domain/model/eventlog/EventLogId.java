package org.dsu.eventlog.domain.model.eventlog;

import org.dsu.eventlog.domain.model.common.BoxId;

public final class EventLogId extends BoxId<Long> {

    private EventLogId(Long id) {
        super(id);
    }

    public static EventLogId of(Long id) {
        return new EventLogId(id);
    }

    public static EventLogId empty() {
        return new EventLogId(null);
    }

}
