package org.dsu.eventlog.domain.model.eventlog

import spock.lang.Specification

class EventLogRemovedEventSpec extends Specification {

	def "Test the creation of the event."() {
		when:
		def event = new EventLogRemovedEvent(EventLogId.of(1L))
		
		then:
		event.occuredOn() != null
		event.eventLogId() == EventLogId.of(1L)
	}
	
	def "Should throw the NPE when pass null args."() {
		when:
		new EventLogRemovedEvent(null)
		
		then:
		def e = thrown(NullPointerException)
		e.getMessage() == "The argument 'eventLogId' must not be null."
		
		when:
		new EventLogRemovedEvent(EventLogId.of(null))
		
		then:
		e = thrown(NullPointerException)
		e.getMessage() == "The argument 'eventLogId.id' must not be null."
	}
}
