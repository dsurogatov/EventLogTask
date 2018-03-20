package org.dsu.eventlog.domain.model.eventlog

import spock.lang.Specification

class RemoveEventLogCommandSpec extends Specification {

	def "Test the creation of the RemoveEventLogCommand."() {
		when:
		def command = new RemoveEventLogCommand(EventLogId.of(1L))
		
		then:
		command.occuredOn() != null
		command.eventLogId() == EventLogId.of(1L)
	}
	
	def "Should throw the NPE when pass a null value in constructor of the RemoveEventLogCommand."() {
		when:
		new RemoveEventLogCommand(null)
		
		then:
		def e = thrown(NullPointerException)
		e.getMessage() == "The argument 'eventLogId' must not be null."
		
		when:
		new RemoveEventLogCommand(EventLogId.of(null))
		
		then:
		e = thrown(NullPointerException)
		e.getMessage() == "The argument 'eventLogId.id' must not be null."
	}
}
