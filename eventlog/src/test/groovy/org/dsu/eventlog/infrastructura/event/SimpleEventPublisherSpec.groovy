package org.dsu.eventlog.infrastructura.event


import org.dsu.eventlog.domain.event.Event
import org.dsu.eventlog.domain.event.EventListener

import spock.lang.Specification

class SimpleEventPublisherSpec extends Specification {

	def "Should publish the event."() {
		given:
		def listener = Mock(EventListener)
		def publisher = new SimpleEventPublisher(listener)
		
		when:
		publisher.publish(_ as Event)
		
		then:
		1 * listener.eventPerformed(_)
		0 * _
	}
}
