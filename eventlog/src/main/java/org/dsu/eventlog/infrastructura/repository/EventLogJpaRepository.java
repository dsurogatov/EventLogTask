package org.dsu.eventlog.infrastructura.repository;

import org.dsu.eventlog.domain.model.eventlog.EventLog;
import org.dsu.eventlog.domain.model.eventlog.EventLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
interface EventLogJpaRepository extends JpaRepository<EventLog, Long>, EventLogRepository {

}
