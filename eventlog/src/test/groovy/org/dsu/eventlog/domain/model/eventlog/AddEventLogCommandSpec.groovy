package org.dsu.eventlog.domain.model.eventlog

import spock.lang.Specification

class AddEventLogCommandSpec extends Specification {

	def "Test creating the AddEventLog command."(){
		when:
		def command = new AddEventLogCommand("name", "description")

		then:
		command.occuredOn() != null
		command.name() == "name"
		command.description() == "description"
	}
}
