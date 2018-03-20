package org.dsu.eventlog.application

import java.time.LocalDate

import org.dsu.eventlog.application.dto.EventLogSummaryDto
import org.dsu.eventlog.domain.common.SpanDate
import org.dsu.eventlog.domain.service.EventLogSummary
import org.dsu.eventlog.presentation.EventLogSummaryController

import spock.lang.Specification

class EventLogSummaryControllerSpec extends Specification {

    EventLogSummaryAppBean eventLogSummaryAppBean = Mock()
    EventLogSummaryController eventLogSummaryController = new EventLogSummaryController(eventLogSummaryAppBean)

    def "Test 'summary' method." () {
        when:
        EventLogSummaryDto summary = eventLogSummaryController.summary(1)

        then:
        1 * eventLogSummaryAppBean.summary({it == 1}) >> {new EventLogSummaryDto(countItems: 2, sumValues: 34)}
        summary.countItems == 2
        summary.sumValues == 34
        0 * _
    }

    def "Test 'summaryDivideByDays' method." () {
        given:
        def from = LocalDate.now().minusDays(1)
        def to = LocalDate.now()

        when:
        List<EventLogSummaryDto> summaries = eventLogSummaryController.summaryDivideByDays(
                1, from.toString(), to.toString())

        then:
        1 * eventLogSummaryAppBean.summaryDivideByDays({ it == 1 }, {
            it == from.toString()
        }, {
            it == to.toString()
        }) >> {
            [
                new EventLogSummaryDto(countItems: 2, sumValues: 34)
            ]
        }
        summaries.size() == 1
        summaries[0].countItems == 2
        summaries[0].sumValues == 34
        0 * _
    }
}
