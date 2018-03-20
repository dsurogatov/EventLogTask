package org.dsu.eventlog.application

import java.util.Optional

import org.dsu.eventlog.domain.model.eventlog.EventLogId
import org.dsu.eventlog.domain.model.eventlogitem.AddEventLogItemCommand
import org.dsu.eventlog.infrastructura.command.CommandExecutor

import spock.lang.Specification

class EventLogItemAppBeanSpec extends Specification {

	def "Test add a value to the EventLog."(){
		given:
		def commandExecutor = Mock(CommandExecutor)
		def eventLogItemAppBean = new EventLogItemAppBean(commandExecutor)
		
		when:
		eventLogItemAppBean.addIntValue(1L, 10)
		
		then:
		1 * commandExecutor.execute({ AddEventLogItemCommand cmd ->
			cmd.eventLogId == EventLogId.of(1L) && cmd.value == 10 && cmd.occuredOn() != null
		}) >> { Optional.empty() }
		0 * _
	}
}
