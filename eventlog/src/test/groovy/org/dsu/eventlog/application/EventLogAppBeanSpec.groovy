package org.dsu.eventlog.application

import static org.dsu.eventlog.util.BeanBuilder.eventLog

import org.dsu.eventlog.application.dto.RefDto
import org.dsu.eventlog.domain.model.common.NameDescription
import org.dsu.eventlog.domain.model.eventlog.AddEventLogCommand
import org.dsu.eventlog.domain.model.eventlog.EventLogId
import org.dsu.eventlog.domain.model.eventlog.EventLogRepository
import org.dsu.eventlog.domain.model.eventlog.RemoveEventLogCommand
import org.dsu.eventlog.domain.model.eventlog.UpdateEventLogCommand
import org.dsu.eventlog.infrastructura.command.CommandExecutor

import spock.lang.Specification

class EventLogAppBeanSpec extends Specification {

    def commandExecutor = Mock(CommandExecutor)
    def eventLogRepository = Mock(EventLogRepository)

    def "Test 'create' method."() {
        given:
        def eventLogAppBean = new EventLogAppBean(commandExecutor, null)

        when:
        def id = eventLogAppBean.create("name", "description")

        then:
        id == 1L
        1 * commandExecutor.execute({AddEventLogCommand cmd ->
            cmd.name == "name" && cmd.description == "description"
        }) >> {
            Optional.of(EventLogId.of(1L))
        }
        0 * _
    }

    def "Should throw the NPE when EventLog does not create."() {
        given:
        def eventLogAppBean = new EventLogAppBean(commandExecutor, null)

        when:
        eventLogAppBean.create("name", "description")

        then:
        thrown(NoSuchElementException)
        1 * commandExecutor.execute(_) >> { Optional.empty() }
        0 * _
    }

    def "Test 'update' method."() {
        given:
        def eventLogAppBean = new EventLogAppBean(commandExecutor, null)

        when:
        eventLogAppBean.update(new RefDto(1L, "name", "description"))

        then:
        1 * commandExecutor.execute({UpdateEventLogCommand cmd ->
            cmd.nameDescription == new NameDescription( "name", "description")
        })
        0 * _
    }
    
    def "Should throw the NPE when pass a null value to the 'update' method."() {
        when:
        new EventLogAppBean(null, null).update(null)

        then:
        NullPointerException npe = thrown()
        npe.message == "The argument 'eventLogRef' must not be null."
    }

    def "Test 'remove' method."() {
        given:
        def eventLogAppBean = new EventLogAppBean(commandExecutor, null)

        when:
        eventLogAppBean.remove(1L)

        then:
        1 * commandExecutor.execute({RemoveEventLogCommand cmd ->
            cmd.eventLogId == EventLogId.of(1L)
        })  >> { Optional.empty() }
        0 * _
    }

    def "Test 'list' method."() {
        given:
        def eventLogAppBean = new EventLogAppBean(null, eventLogRepository)

        when:
        List<RefDto> dtos = eventLogAppBean.list()

        then:
        dtos[0] == new RefDto(1L, "name1", "description1")
        dtos[1] == new RefDto(2L, "name2", "description2")
        1 * eventLogRepository.findAll() >> [
            eventLog(1L, "name1", "description1"),
            eventLog(2L, "name2", "description2")
        ]
        0 * _
    }
    
    def "Test 'one' method."() {
        given:
        def eventLogAppBean = new EventLogAppBean(null, eventLogRepository)

        when:
        RefDto dto = eventLogAppBean.one(1)

        then:
        dto == new RefDto(1, "name1", "description1")
        1 * eventLogRepository.findById({it == 1}) >> Optional.of(eventLog(1L, "name1", "description1"))
        0 * _
    }
    
    def "Should throw IllegalStateException the the EventLog will not be found."() {
        given:
        def eventLogAppBean = new EventLogAppBean(null, eventLogRepository)
        
        when:
        eventLogAppBean.one(1)
        
        then:
        def e = thrown(IllegalStateException)
        e.message == "There is no the EventLog with id=1"
        1 * eventLogRepository.findById({it == 1}) >> Optional.empty()
        0 * _
    }
}
