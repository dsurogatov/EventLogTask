package org.dsu.eventlog.domain.model.eventlogitem

import org.dsu.eventlog.domain.event.EventPublisher
import org.dsu.eventlog.domain.model.common.EntityRepository
import org.dsu.eventlog.domain.model.eventlog.EventLog
import org.dsu.eventlog.domain.model.eventlog.EventLogId

import spock.lang.Specification

class AddEventLogItemCommandHandlerSpec extends Specification {

	def "Handle the command by the AddEventLogItemCommandHandler class."() {
		given:
		EntityRepository repository = Mock()
		def eventPublisher = Mock(EventPublisher)
		def command = new AddEventLogItemCommand(EventLogId.of(1L), 23)
		def handler = new AddEventLogItemCommandHandler(repository);

		when:
		def ret = handler.handle(command)

		then:
		ret.isPresent() == false
		1 * repository.findRequired(EventLog.class, EventLogId.of(1L)) >> eventLogWithId()
		1 * repository.add({
			assert it.getClass() == EventLogItemIntValue
			assert it.eventLogId().id() == 1l
			assert it.occuredDate() == command.occuredOn().toLocalDate()
			assert it.occuredTime() == command.occuredOn().toLocalTime()
			it.value() == 23
		}) >> { EventLogItemIntValue item ->
			item.id = 12
			item.eventPublisher(eventPublisher)
		}
		1 * eventPublisher.publish(_)
		0 * _
	}

	private EventLog eventLogWithId() {
		EventLog eventLog = new EventLog("name", "description")
		eventLog.id = 1
		return eventLog
	}

	def "Should throw the exception when passed null args."() {
		when:
		new AddEventLogItemCommandHandler(_ as EntityRepository).handle(null)

		then:
		thrown(NullPointerException)
	}
}
