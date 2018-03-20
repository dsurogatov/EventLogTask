package org.dsu.eventlog.domain.model.common

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

import spock.lang.Specification

class SeparateDateTimeSpec extends Specification {

	def "Test the creation of the SeparateDateTime class."() {
		when:
		def separateDateTime = new SeparateDateTime(LocalDateTime.of(2018, 1, 6, 22, 33))
		
		then:
		separateDateTime.occuredDate() == LocalDate.of(2018, 1, 6)
		separateDateTime.occuredTime() == LocalTime.of(22, 33)
	}
	
	def "Fail the creation of the SeparateDateTime class when was passed null value."() {
		when:
		def separateDateTime = new SeparateDateTime(null)
		
		then:
		def e = thrown(NullPointerException)
		e.message == "The argument 'occuredOn' must not be null."
	}
}
