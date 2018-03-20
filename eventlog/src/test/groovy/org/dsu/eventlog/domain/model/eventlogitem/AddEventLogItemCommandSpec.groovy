package org.dsu.eventlog.domain.model.eventlogitem

import static org.dsu.eventlog.domain.model.eventlog.EventLogId.of

import org.dsu.eventlog.domain.model.eventlog.EventLogId

import spock.lang.Specification

class AddEventLogItemCommandSpec extends Specification {

	def "Test creating the AddEventLogItemCommand class."() {
		setup:
		def command = new AddEventLogItemCommand(of(1L), 23)
		
		expect:
		command.occuredOn() != null
		command.eventLogId() == of(1L)
		command.value() == 23
	}
	
	def "Should throw NPE when pass a null value of the eventLogId in the constructor."() {
		when:
		new AddEventLogItemCommand(null, 23)
		
		then:
		def e = thrown(NullPointerException)
		e.message == "The argument 'eventLogId' must not be null." 
	}
}
