package org.dsu.eventlog.presentation

import org.dsu.eventlog.application.EventLogItemAppBean

import spock.lang.Specification

class EventLogItemControllerSpec extends Specification {

	def "Test 'addIntValue' method."() {
		given:
		def eventLogItemAppBean = Mock(EventLogItemAppBean)
		def eventLogItemController = new EventLogItemController(eventLogItemAppBean)

		when:
		eventLogItemController.addIntValue(1, 10)

		then:
		1 * eventLogItemAppBean.addIntValue({it==1}, {it==10})
		0 * _
	}
}
