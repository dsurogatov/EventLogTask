package org.dsu.eventlog.infrastructura.event

import org.dsu.eventlog.domain.event.Event
import org.dsu.eventlog.domain.event.EventHandler

import spock.lang.Specification

class SimpleEventListenerSpec extends Specification {
	
	def listener = new SimpleEventListener()

	def "Should pass the event to the registered handler."() {
		given:
		def handler = Mock(EventHandler)
		listener.register(handler)
		
		when:
		listener.eventPerformed(_ as Event)
		
		then:
		1 * handler.canHandle(_) >> true
		1 * handler.handle(_ as Event)
		0 * _
	}
	
	def "Should not pass the event to the registered handler."() {
		given:
		def handler = Mock(EventHandler)
		def listener = new SimpleEventListener()
		listener.register(handler)
		
		when:
		listener.eventPerformed(_ as Event)
		
		then:
		1 * handler.canHandle(_) >> false
		0 * _
	}
	
	def "Should throw an exception when try to register a null value."() {
		when:
		listener.register(null)
		
		then:
		def e = thrown(NullPointerException)
		e.message == "The argument 'eventHandler' must not be null."
	}
	
	def "Should process a null value of the Event without any invocations."() {
		given:
		def handler = Mock(EventHandler)
		listener.register(handler)
		
		when:
		listener.eventPerformed(null)
		
		then:
		0 * _
	}
}
