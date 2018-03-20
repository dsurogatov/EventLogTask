package org.dsu.eventlog.domain.model.eventlog;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.dsu.eventlog.domain.model.common.AbstractEntity;
import org.dsu.eventlog.domain.model.common.EntityRepository;
import org.dsu.eventlog.domain.model.common.NameDescription;

import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "eventlog")
@ToString(callSuper = true, includeFieldNames = true)
@NoArgsConstructor
public class EventLog extends AbstractEntity {

    private NameDescription nameDescription;

    public EventLog(String name, String description) {
        setNameDescription(new NameDescription(name, description));
    }

    private void setNameDescription(NameDescription nameDescription) {
        checkNotNull(nameDescription, argNotBeNull("nameDescription"));
        this.nameDescription = nameDescription;
    }

    public String name() {
        return nameDescription.name();
    }

    public void setName(String name) {
        nameDescription = new NameDescription(name, nameDescription.description());
    }

    public String description() {
        return nameDescription.description();
    }

    public void setDescription(String description) {
        nameDescription = new NameDescription(nameDescription.name(), description);
    }

    public EventLogId eventLogId() {
        return EventLogId.of(id());
    }

    public void create(EntityRepository entityRepository) {
        checkNotNull(entityRepository, argNotBeNull("entityRepository"));
        checkState(id() == null, "It is not allowed to create the EventLog's entity with the existed id.");

        entityRepository.add(this);
        eventPublisher().publish(new EventLogCreatedEvent(eventLogId()));
    }

    public void update(NameDescription nameDescription, EntityRepository entityRepository) {
        checkNotNull(entityRepository, argNotBeNull("entityRepository"));
        checkState(id() != null, "It is not allowed to update the EventLog's entity without the id.");

        setNameDescription(nameDescription);
        entityRepository.update(this);
        eventPublisher().publish(new EventLogUpdatedEvent(eventLogId()));
    }

    public void remove(EntityRepository entityRepository) {
        checkNotNull(entityRepository, argNotBeNull("entityRepository"));
        checkState(id() != null, "It is not allowed to remove the EventLog's entity without the id.");

        entityRepository.remove(this);
        eventPublisher().publish(new EventLogRemovedEvent(eventLogId()));
    }

}
