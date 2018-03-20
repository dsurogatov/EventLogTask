package org.dsu.eventlog.domain.model.eventlog;

import java.util.List;
import java.util.Optional;

public interface EventLogRepository {

    List<EventLog> findAll();
    Optional<EventLog> findById(Long id);
}
