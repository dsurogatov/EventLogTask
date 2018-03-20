package org.dsu.eventlog.domain.common

import static java.time.LocalDate.now

import java.time.LocalDate

import spock.lang.Specification

class SpanDateSpec extends Specification {

	def "Test the creation of the SpanDate class."() {
		when:
		def spanDate = new SpanDate(now(), LocalDate.now().plusDays(1))
		
		then:
		spanDate.begin() == LocalDate.now()
		spanDate.end() == LocalDate.now().plusDays(1)
	}
	
	def "Should throw the exception when passed wrong args."() {
		when:
		def spanDate = new SpanDate(begin, end)
		
		then:
		def e = thrown(Exception)
		expectedException.isInstance(e)
		e.message == message
		
		where:
		begin | end                | expectedException     | message
		null  | now()              | NullPointerException  | "The argument 'begin' must not be null."
		now() | null               | NullPointerException  | "The argument 'end' must not be null."
		now() | now().minusDays(1) | IllegalStateException | "The argument 'end' should be less or equal to 'begin' argument."
		
	}
}
