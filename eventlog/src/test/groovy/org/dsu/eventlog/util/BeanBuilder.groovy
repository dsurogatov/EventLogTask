package org.dsu.eventlog.util

import java.time.LocalDate

import org.dsu.eventlog.domain.model.common.SeparateDateTime
import org.dsu.eventlog.domain.model.eventlog.EventLog
import org.dsu.eventlog.domain.model.eventlogitem.EventLogItemIntValue

class BeanBuilder {

    static def eventLog(Long id, String name, String description) {
        def eventLog = new EventLog(name, description)
        eventLog.id = id
        eventLog
    }

    static def eventLogItem(int value, LocalDate when) {
        def eventLogItem = new EventLogItemIntValue(value)
        eventLogItem.occuredOn(new SeparateDateTime(when.atTime(0, 0)))
        return eventLogItem
    }
}
