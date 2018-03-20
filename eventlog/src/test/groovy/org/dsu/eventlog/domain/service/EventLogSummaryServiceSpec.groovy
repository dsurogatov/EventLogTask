package org.dsu.eventlog.domain.service

import static java.time.LocalDate.now

import static org.dsu.eventlog.util.BeanBuilder.eventLogItem

import java.time.LocalDate
import java.time.LocalDateTime

import org.dsu.eventlog.domain.common.SpanDate
import org.dsu.eventlog.domain.model.common.SeparateDateTime
import org.dsu.eventlog.domain.model.eventlog.EventLog
import org.dsu.eventlog.domain.model.eventlog.EventLogId
import org.dsu.eventlog.domain.model.eventlogitem.EventLogItemIntValue
import org.dsu.eventlog.domain.model.eventlogitem.EventLogItemRepository
import org.dsu.eventlog.domain.model.eventlogitem.EventLogItemSpec
import org.dsu.eventlog.util.BeanBuilder

import spock.lang.Ignore
import spock.lang.Specification

class EventLogSummaryServiceSpec extends Specification {

    def eventLogId = EventLogId.of(1L)
    def spanDate = new SpanDate(now().minusDays(1), now())
    EventLogItemRepository eventLogItemRepository = Mock()
    def service = new EventLogSummaryService(eventLogItemRepository)

    def "Should calc zero sum and count if there are no eventLogItems."() {
        when:
        def summary = service.calcSummary(eventLogId, spanDate)

        then:
        1 * eventLogItemRepository.findByEventLogIdAndOccuredOnOccuredDateBetween(*_) >> []
        summary.countItems() == 0
        summary.sumValues() == 0
        0 * _
    }

    def "Should calc sum and count."() {
        when: "one item"
        def summary = service.calcSummary(eventLogId, spanDate)

        then:
        1 * eventLogItemRepository.findByEventLogIdAndOccuredOnOccuredDateBetween(*_) >> eventLogItesByValues([2])
        summary.countItems() == 1
        summary.sumValues() == 2
        0 * _

        when: "several items"
        summary = service.calcSummary(eventLogId, spanDate)

        then:
        1 * eventLogItemRepository.findByEventLogIdAndOccuredOnOccuredDateBetween(*_) >> eventLogItesByValues([2, 3, 4])
        summary.countItems() == 3
        summary.sumValues() == 9
        0 * _
    }

    private eventLogItesByValues(List values) {
        def eventLogItems = []
        values.each({
            def item = new EventLogItemIntValue(it)
            item.eventLog(eventLog()).occuredOn(new SeparateDateTime(LocalDateTime.now()))
            eventLogItems << item
        })
        eventLogItems
    }

    private eventLog() {
        new EventLog("name", "description")
    }

    def "Should throw exception when passed arguments are null."() {

        when: 'pass null eventLogId then throw NPE'
        service.calcSummary(null, spanDate)

        then:
        def npe = thrown(NullPointerException)
        npe.message == "The argument 'eventLogId' must not be null."

        when: 'pass null generic id of the eventLog then throw NPE'
        service.calcSummary(EventLogId.empty(), spanDate)

        then:
        npe = thrown(NullPointerException)
        npe.message == "The argument 'eventLogId.id' must not be null."

        when: 'pass a null value of the SpanDate then throw NPE'
        service.calcSummary(eventLogId, null)

        then:
        npe = thrown(NullPointerException)
        npe.message == "The argument 'spanDate' must not be null."
    }

    def "Test calculating the summary by days"() {
        given:
        def itemValues = [
            eventLogItem(1, now()),
            eventLogItem(2, now()),
            eventLogItem(5, now().minusDays(1))
        ]

        when:
        Map<LocalDate, EventLogSummary> report = service.calcSummaryDivideByDays(eventLogId, spanDate)

        then:
        1 * eventLogItemRepository.findByEventLogIdAndOccuredOnOccuredDateBetween(
                { it == eventLogId.id() },{ it == spanDate.begin() }, { it == spanDate.end() }) >> itemValues
        report.size() == 2
        report[now()] == new EventLogSummary(2, 3)
        report[now().minusDays(1)] == new EventLogSummary(1, 5)
        0 * _
    }

    def "Should return empty map if there are no events in the span of dates."() {
        when:
        Map<LocalDate, EventLogSummary> report = service.calcSummaryDivideByDays(eventLogId, spanDate)

        then:
        1 * eventLogItemRepository.findByEventLogIdAndOccuredOnOccuredDateBetween(
                { it == eventLogId.id() },{ it == spanDate.begin() }, { it == spanDate.end() }) >> []
        report.size() == 0
        0 * _
    }

    def "Test exceptions when pass wrong arguments to 'calcSummaryDivideByDays'."() {
        
        when: 'pass a null value of the EventLog'
        service.calcSummaryDivideByDays(null, spanDate)

        then:
        def npe = thrown(NullPointerException)
        npe.message == "The argument 'eventLogId' must not be null."

        when: 'pass null generic id of the eventLog then throw NPE'
        service.calcSummaryDivideByDays(EventLogId.empty(), spanDate)

        then:
        npe = thrown(NullPointerException)
        npe.message == "The argument 'eventLogId.id' must not be null."
        
        when: 'pass a null value of the SpanDate then throw NPE'
        service.calcSummaryDivideByDays(eventLogId, null)

        then:
        npe = thrown(NullPointerException)
        npe.message == "The argument 'spanDate' must not be null."
        
    }
}
