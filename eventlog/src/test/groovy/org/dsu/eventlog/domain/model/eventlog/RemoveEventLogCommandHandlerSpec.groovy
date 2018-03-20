package org.dsu.eventlog.domain.model.eventlog

import org.dsu.eventlog.domain.event.EventPublisher
import org.dsu.eventlog.domain.model.common.EntityRepository
import org.dsu.eventlog.domain.model.eventlogitem.EventLogItemRepository
import org.dsu.eventlog.util.BeanBuilder

import spock.lang.Specification

class RemoveEventLogCommandHandlerSpec extends Specification {

    def "Test the RemoveEventLogCommandHandler's 'handle' method"() {
        given:
        def publisher = Mock(EventPublisher)
        EventLog eventLog = BeanBuilder.eventLog(1, "name", "description")
        eventLog.eventPublisher(publisher)
        def command = new RemoveEventLogCommand(EventLogId.of(1L))
        def entityRepository = Mock(EntityRepository)
        def eventLogItemRepository = Mock(EventLogItemRepository)
        def handler = new RemoveEventLogCommandHandler(entityRepository, eventLogItemRepository)

        when:
        def ret = handler.handle(command)

        then:
        ret.isPresent() == false
        1 * entityRepository.findRequired(EventLog.class, EventLogId.of(1L)) >> eventLog
        1 * eventLogItemRepository.deleteByEventLogId(1L)
        1 * entityRepository.remove({ EventLog el ->
            el.id() == 1L
        })
        1 * publisher.publish({EventLogRemovedEvent e -> e.eventLogId() == EventLogId.of(1) })
        0 * _
    }

    def "Should throw the NPE when pass a null value to 'handle' method."() {
        when:
        new RemoveEventLogCommandHandler(Mock(EntityRepository), Mock(EventLogItemRepository)).handle(null)

        then:
        def e = thrown(NullPointerException)
        e.getMessage() == "The argument 'command' must not be null."
    }
}
