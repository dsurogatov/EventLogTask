package org.dsu.eventlog.domain.model.eventlogitem;

import java.time.LocalDate;
import java.util.List;

public interface EventLogItemRepository {

    List<EventLogItemIntValue> findByEventLogIdAndOccuredOnOccuredDateBetween(Long eventLogLongId, LocalDate begin,
            LocalDate end);
    
    void deleteByEventLogId(Long eventLogId);
}
