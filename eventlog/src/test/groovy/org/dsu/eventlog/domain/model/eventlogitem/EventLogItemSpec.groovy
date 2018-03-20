package org.dsu.eventlog.domain.model.eventlogitem

import static java.time.LocalDateTime.now

import org.dsu.eventlog.domain.event.EventPublisher
import org.dsu.eventlog.domain.model.common.EntityRepository
import org.dsu.eventlog.domain.model.eventlog.EventLog
import org.dsu.eventlog.domain.model.eventlog.EventLogId

import spock.lang.Specification

class EventLogItemSpec extends Specification {
	
	def entityRepository = Mock(EntityRepository)

	def "Test creating the EventLogItem class."() {
		setup:
		EventLogItemIntValue item = new EventLogItemIntValue(1)

		expect:
		item.id() == null
		item.gid().length() == 36
		item.eventLogId() == null
		item.occuredDate() == null
		item.occuredTime() == null
		item.value() == 1
	}

	def "Test 'commitToEventLog' method."() {
		given:
		def when = now()
		def eventLogId = EventLogId.of(1L)
		def publisher = Mock(EventPublisher)
		EventLogItemIntValue item = new EventLogItemIntValue(1)
		item.eventPublisher(publisher)

		when:
		item.commitToEventLog(eventLogId, when, entityRepository)

		then:
		item.id() == 100
		item.gid().length() == 36
		item.eventLogId() == eventLogId
		item.occuredDate() == when.toLocalDate()
		item.occuredTime() == when.toLocalTime()
		item.value() == 1
		1 * entityRepository.findRequired(EventLog.class, eventLogId) >> eventLogWithId()
		1 * entityRepository.add({
			it.id = 100
		})
		1 * publisher.publish(_)
		0 * _
	}

	private EventLog eventLogWithId() {
		EventLog eventLog = new EventLog("name", "description")
		eventLog.id = 1
		return eventLog
	}

	def "Should throw the exception when passed null args to 'commitToEventLog' method."() {
		given:
		def item = new EventLogItemIntValue(1)
		
		when:
		item.commitToEventLog(eventLogId, when, entityRepository)

		then:
		def exception = thrown(NullPointerException)
		exception.message == message

		where:
		eventLogId          | when  | message
		null                | now() | "The argument 'eventLogId' must not be null."
		EventLogId.of(null) | now() | "The argument 'eventLogId.id' must not be null."
		EventLogId.of(1L)   | null  | "The argument 'when' must not be null."
	}
	
	def "Should throw the exception when passed a null value of EntityRepository to 'commitToEventLog' method."() {
		when:
		new EventLogItemIntValue(1).commitToEventLog(EventLogId.of(1L), now(), null)
		
		then:
		def exception = thrown(NullPointerException)
		exception.message == "The argument 'entityRepository' must not be null."
	}

}
