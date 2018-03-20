package org.dsu.eventlog.application

import static java.time.LocalDate.now

import java.time.DateTimeException
import java.time.LocalDate

import org.dsu.eventlog.application.dto.EventLogSummaryDto
import org.dsu.eventlog.domain.common.SpanDate
import org.dsu.eventlog.domain.model.eventlog.EventLogId
import org.dsu.eventlog.domain.service.EventLogSummary
import org.dsu.eventlog.domain.service.EventLogSummaryService
import org.hibernate.cfg.ExtendsQueueEntry

import spock.lang.Specification

class EventLogSummaryAppBeanSpec extends Specification {

    EventLogSummaryService eventLogSummaryService = Mock(EventLogSummaryService)
    EventLogSummaryAppBean eventLogSummaryAppBean = new EventLogSummaryAppBean(eventLogSummaryService)

    def "Test 'summary' method."() {
        when:
        EventLogSummaryDto eventLogSummaryDto = eventLogSummaryAppBean.summary(1L)

        then:
        1 * eventLogSummaryService.calcSummary( {
            it == EventLogId.of(1L)
        }, {
            it == new SpanDate(now(), now())
        }) >> {
            new EventLogSummary(2, 100)
        }
        eventLogSummaryDto.countItems == 2
        eventLogSummaryDto.sumValues == 100
        0 * _
    }

    def "Test the 'summaryDivideByDays' method."() {
        given:
        def from = now().minusDays(1)
        def to = now()
        def returnedByDomainServiceMap = [:]
        returnedByDomainServiceMap[to] = new EventLogSummary(2, 100)
        returnedByDomainServiceMap[from] = new EventLogSummary(5, 200)

        when:
        List<EventLogSummaryDto> dtos = eventLogSummaryAppBean.summaryDivideByDays(1, from.toString(), to.toString())

        then:
        1 * eventLogSummaryService.calcSummaryDivideByDays( {
            it == EventLogId.of(1L)
        }, {
            it == new SpanDate(now().minusDays(1), now())
        }) >> returnedByDomainServiceMap
        dtos.size() == 2
        dtos[0].date == from.toString()
        dtos[0].countItems == 5
        dtos[0].sumValues == 200
        dtos[1].date == to.toString()
        dtos[1].countItems == 2
        dtos[1].sumValues == 100
        0 * _
    }
    
    def "Test exceptions when pass wrong arguments."() {
        
        when: "when pass a null value of the 'from' arg"
        eventLogSummaryAppBean.summaryDivideByDays(1, null, null)
        
        then:
        def npe = thrown(NullPointerException)
        npe.message == "The argument 'from' must not be null."
        
        when: "when pass a null value of the 'to' arg"
        eventLogSummaryAppBean.summaryDivideByDays(1, '2017-01-01', null)
        
        then:
        npe = thrown(NullPointerException)
        npe.message == "The argument 'to' must not be null."
        
        when: "when pass a wrong date format value of the 'from' arg"
        eventLogSummaryAppBean.summaryDivideByDays(1, 'wrong', '')
        
        then:
        def e = thrown(DateTimeException)
        
        when: "when pass a wrong date format value of the 'to' arg"
        eventLogSummaryAppBean.summaryDivideByDays(1, '2017-01-01', 'wrong')
        
        then:
        e = thrown(DateTimeException)
    }
}
