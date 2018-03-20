package org.dsu.eventlog.domain.model.eventlog

import java.util.Optional

import org.dsu.eventlog.domain.event.EventPublisher
import org.dsu.eventlog.domain.model.common.EntityRepository
import org.dsu.eventlog.domain.model.common.NameDescription

import spock.lang.Specification

class UpdateEventLogCommandHandlerSpec extends Specification {

    def "Test the UpdateEventLogCommandHandler.handle() method."() {
        given:
        EntityRepository repository = Mock()
        EventPublisher publisher = Mock()

        EventLog findedEventLog = new EventLog("name", "descr")
        findedEventLog.id = 1L
        findedEventLog.eventPublisher(publisher)
        
        def command = new UpdateEventLogCommand(EventLogId.of(1L), new NameDescription("name1", "descr1"))
        def handler = new UpdateEventLogCommandHandler(repository)

        when:
        def ret = handler.handle(command)

        then:
        ret == Optional.empty()
        1 * repository.findRequired(EventLog.class, {it == EventLogId.of(1L)}) >> findedEventLog
        1 * repository.update({ EventLog e ->
            e.id() == 1 && e.name() == "name1" && e.description() == "descr1"
        })
        1 * publisher.publish(_)
        0 * _
    }
    
    def "Should throw the NPE when will pass a null value of the Command."() {
        when:
        new UpdateEventLogCommandHandler(null).handle(null)
        
        then:
        def e = thrown(NullPointerException)
        e.getMessage() == "The argument 'command' must not be null."
    }
}
