package org.dsu.eventlog.presentation

import org.dsu.eventlog.application.EventLogAppBean
import org.dsu.eventlog.application.dto.RefDto

import spock.lang.Specification

class EventLogControllerSpec extends Specification {
	
	def eventLogAppBean = Mock(EventLogAppBean)
	def eventLogController = new EventLogController(eventLogAppBean)

	def "Test 'create' method of the EventLogController."() {
		when:
		def eventLogId = eventLogController.create(new RefDto(name: "name", description: "description"))
		
		then:
		1 * eventLogAppBean.create({it == "name"}, {it == "description"}) >> { 1L }
		eventLogId == 1
		0 * _
	}
    
    def "Test 'update' method of the EventLogController."() {
        when:
        eventLogController.update(new RefDto(1, "name", "descr"))
        
        then:
        1 * eventLogAppBean.update({it == new RefDto(1, "name", "descr")})
        0 * _
    }
	
	def "Test 'remove' method of the EventLogController."() {
		when:
		eventLogController.remove(1)
		
		then:
		1 * eventLogAppBean.remove({it == 1}) 
		0 * _
	}
	
	def "Test 'list' method of the EventLogController."() {
		setup:
		def dto1 = new RefDto(1, "name", "description")
		def dto2 = new RefDto(2, "name2", "description2")
		
		when:
		def dtos = eventLogController.list()
		
		then:
		1 * eventLogAppBean.list() >> [dto1, dto2]
		dtos[0] == dto1 
		dtos[1] == dto2 
		0 * _
	}
    
    def "Test 'one' method of the EventLogController."() {
        setup:
        def dto = new RefDto(1, "name", "description")
        
        when:
        def retDto = eventLogController.one(1)
        
        then:
        1 * eventLogAppBean.one({it == 1}) >> dto
        retDto == dto
        0 * _
    }
    
}
