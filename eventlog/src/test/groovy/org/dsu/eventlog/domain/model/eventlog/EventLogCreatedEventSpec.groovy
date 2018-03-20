package org.dsu.eventlog.domain.model.eventlog

import spock.lang.Specification

class EventLogCreatedEventSpec extends Specification {

	def "Test the creation of the event."() {
		when:
		def event = new EventLogCreatedEvent(EventLogId.of(1L))
		
		then:
		event.occuredOn() != null
		event.eventLogId() == EventLogId.of(1L)
	}
	
	def "Should throw the NPE when will pass null args."() {
		when:
		new EventLogCreatedEvent(null)
		
		then:
		def e = thrown(NullPointerException)
		e.getMessage() == "The argument 'eventLogId' must not be null."
		
		when:
		new EventLogCreatedEvent(EventLogId.of(null))
		
		then:
		e = thrown(NullPointerException)
		e.getMessage() == "The argument 'eventLogId.id' must not be null."
	}
}
