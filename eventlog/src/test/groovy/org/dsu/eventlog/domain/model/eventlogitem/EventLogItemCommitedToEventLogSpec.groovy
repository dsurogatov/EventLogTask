package org.dsu.eventlog.domain.model.eventlogitem

import org.dsu.eventlog.domain.model.eventlog.EventLogId

import spock.lang.Specification

class EventLogItemCommitedToEventLogSpec extends Specification {

	def "Test creating the EventLogItemCommitedToEventLog event."() {
		when:
		def event = new EventLogItemCommitedToEventLogEvent(EventLogId.of(1L), EventLogItemId.of(2L))

		then:
		event.occuredOn() != null
		event.eventLogId() == EventLogId.of(1L)
		event.eventLogItemId() == EventLogItemId.of(2L)
	}

	def "Should throw an exception when will pass null values of args."() {
		when:
		new EventLogItemCommitedToEventLogEvent(null, EventLogItemId.of(2L))

		then:
		def e = thrown(NullPointerException)
		e.message == "The argument 'eventLogId' must not be null."

		when:
		new EventLogItemCommitedToEventLogEvent(EventLogId.of(null), EventLogItemId.of(2L))

		then:
		e = thrown(NullPointerException)
		e.message == "The argument 'eventLogId.id' must not be null."

		when:
		new EventLogItemCommitedToEventLogEvent(EventLogId.of(2L), null)

		then:
		e = thrown(NullPointerException)
		e.message == "The argument 'eventLogItemId' must not be null."

		when:
		new EventLogItemCommitedToEventLogEvent(EventLogId.of(2L), EventLogItemId.of(null))

		then:
		e = thrown(NullPointerException)
		e.message == "The argument 'eventLogItemId.id' must not be null."
	}
}
