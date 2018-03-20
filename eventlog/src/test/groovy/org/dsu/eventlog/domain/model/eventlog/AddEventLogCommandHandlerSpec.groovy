package org.dsu.eventlog.domain.model.eventlog

import org.dsu.eventlog.domain.model.common.EntityRepository

import spock.lang.Specification

class AddEventLogCommandHandlerSpec extends Specification {

	def "Test the AddEventLogCommandHandler.handle() method."() {
		given:
		def command = new AddEventLogCommand("name", "description")
		def repository = Mock(EntityRepository)
		def handler = new AddEventLogCommandHandler(repository)

		when:
		def ret = handler.handle(command)

		then:
		ret.get().id() == 100
		1 * repository.add(_) >> {EventLog eventLog ->
			eventLog.id = 100
			eventLog
		}
		0 * _
	}
	
	def "Should throw the NPE when will pass a null value of Command."() {
		when:
		new AddEventLogCommandHandler(null).handle(null)
		
		then:
		def e = thrown(NullPointerException)
		e.getMessage() == "The argument 'command' must not be null."
	}
}
