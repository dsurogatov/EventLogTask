package org.dsu.eventlog.domain.model.eventlog

import static org.dsu.eventlog.util.BeanBuilder.eventLog

import org.dsu.eventlog.domain.event.EventPublisher
import org.dsu.eventlog.domain.model.common.EntityRepository
import org.dsu.eventlog.domain.model.common.NameDescription
import org.dsu.eventlog.util.BeanBuilder

import spock.lang.Specification

class EventLogSpec extends Specification {
    
    EntityRepository repository = Mock()
    EventPublisher publisher = Mock()

    def "Create the EventLog entity successfully."() {
        when:
        def eventLog = new EventLog("name", "description")

        then:
        eventLog.id() == null
        eventLog.gid().length() == 36
        eventLog.name() == "name"
        eventLog.description() == "description"
    }

    def "Should throw NPE then pass a null value to the method setNameDescription."() {
        given:
        def eventLog = new EventLog("name", "description")

        when:
        eventLog.setNameDescription(null)

        then:
        thrown(NullPointerException)
    }

    def "Test changes of name and description fields."() {
        given:
        def eventLog = new EventLog("name", "description")

        when:
        eventLog.setName("name1")
        eventLog.setDescription("description1")

        then:
        eventLog.name() == "name1"
        eventLog.description() == "description1"
    }

    def "Test the 'create' method."() {
        given:
        def eventLog = new EventLog("name", "description")
        eventLog.eventPublisher(publisher)

        when:
        eventLog.create(repository)

        then:
        eventLog.id() == 100L
        1 * repository.add({ EventLog e ->
            e.id = 100L
            true
        })
        1 * publisher.publish({EventLogCreatedEvent event ->
            event.eventLogId().id() == 100L
        })
        0 * _
    }

    def "Should throw the NPE when will pass a null value to the 'create' method."() {
        when:
        new EventLog("name", "description").create(null)

        then:
        def e = thrown(NullPointerException)
        e.getMessage() == "The argument 'entityRepository' must not be null."
    }

    def "Should throw the IllegalStateException when will try to create on the existing EventLog."() {
        given:
        def eventLog = eventLog(1, "name", "description")

        when:
        eventLog.create(repository)

        then:
        def e = thrown(IllegalStateException)
        e.getMessage() == "It is not allowed to create the EventLog's entity with the existed id."
    }

    def "Test the 'update' method."() {
        given:
        def eventLog = eventLog(1, "name", "description")
        eventLog.eventPublisher(publisher)

        when:
        eventLog.update(new NameDescription("name1", "descr2"), repository)

        then:
        eventLog.id() == 1L
        1 * repository.update({ EventLog e ->
            e.id == 1L && e.name() == "name1" && e.description() == "descr2"
        })
        1 * publisher.publish({EventLogUpdatedEvent event ->
            event.eventLogId().id() == 1L
        })
        0 * _
    }
    
    def "Should throw the NPE when will pass a null value to the 'update' method."() {
        when:
        new EventLog("name", "description").update(Mock(NameDescription), null)

        then:
        def e = thrown(NullPointerException)
        e.getMessage() == "The argument 'entityRepository' must not be null."
    }
    
    def "Should throw the IllegalStateException when will try to create on the not existing EventLog."() {
        given:
        def eventLog = new EventLog("name", "description")

        when:
        eventLog.update(Mock(NameDescription), repository)

        then:
        def e = thrown(IllegalStateException)
        e.getMessage() == "It is not allowed to update the EventLog's entity without the id."
    }

    def "Test the 'remove' method."() {
        given:
        def eventLog = eventLog(1, "name", "description")
        eventLog.eventPublisher(publisher)

        when:
        eventLog.remove(repository)

        then:
        eventLog.id() == 1L
        1 * repository.remove({ EventLog e ->
            e.id = 1L
            true
        })
        1 * publisher.publish({EventLogRemovedEvent event ->
            event.eventLogId().id() == 1L
        })
        0 * _
    }

    def "Should throw the NPE when pass a null value to the 'remove' method."() {
        when:
        new EventLog("name", "description").remove(null)

        then:
        def e = thrown(NullPointerException)
        e.getMessage() == "The argument 'entityRepository' must not be null."
    }

    def "Should throw the IllegalStateException when try to remove on the not persisted EventLog."() {
        given:
        def repository = Mock(EntityRepository)
        def eventLog = new EventLog("name", "description")

        when:
        eventLog.remove(repository)

        then:
        def e = thrown(IllegalStateException)
        e.getMessage() == "It is not allowed to remove the EventLog's entity without the id."
    }
}
