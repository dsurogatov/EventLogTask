package org.dsu.eventlog.domain.model.eventlog

import org.dsu.eventlog.domain.model.common.NameDescription

import spock.lang.Specification

class UpdateEventLogCommandSpec extends Specification {

    def "Test creating the UpdateEventLog command."(){
        when:
        def command = new UpdateEventLogCommand(EventLogId.of(1L), new NameDescription("name", "description"))

        then:
        command.occuredOn() != null
        command.nameDescription() == new NameDescription("name", "description")
        command.eventLogId() == EventLogId.of(1L)
    }

    def "Should throw an exception then pass wrong args to the UpdateEventLog command."(){

        when: 'when pass a null EventLogId then will throw NPE'
        new UpdateEventLogCommand(null, Mock(NameDescription))

        then:
        NullPointerException npe = thrown()
        npe.message  == "The argument 'eventLogId' must not be null."
        
        when: 'when pass a null EventLogId.id then will throw NPE'
        new UpdateEventLogCommand(EventLogId.empty(), Mock(NameDescription))

        then:
        npe = thrown()
        npe.message  == "The argument 'eventLogId.id' must not be null."
        
        when: 'when pass a null NameDescription then will throw NPE'
        new UpdateEventLogCommand(EventLogId.of(1L), null)

        then:
        npe = thrown()
        npe.message  == "The argument 'nameDescription' must not be null."
        
    }
}
