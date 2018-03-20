package org.dsu.eventlog.infrastructura.repository;

import org.dsu.eventlog.domain.model.eventlogitem.EventLogItemIntValue;
import org.dsu.eventlog.domain.model.eventlogitem.EventLogItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
interface EventLogItemJpaRepository extends JpaRepository<EventLogItemIntValue, Long>, EventLogItemRepository {

}
