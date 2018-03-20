package org.dsu.eventlog.domain.model.eventlogitem;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Basic;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.dsu.eventlog.domain.model.common.AbstractEntity;
import org.dsu.eventlog.domain.model.common.EntityRepository;
import org.dsu.eventlog.domain.model.common.SeparateDateTime;
import org.dsu.eventlog.domain.model.eventlog.EventLog;
import org.dsu.eventlog.domain.model.eventlog.EventLogId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@MappedSuperclass
@Accessors(fluent = true)
@ToString(callSuper = true, includeFieldNames = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEventLogItem<T> extends AbstractEntity {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PROTECTED)
    private SeparateDateTime occuredOn;

    @ManyToOne
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PROTECTED)
    private EventLog eventLog;

    @Basic
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private T value;

    protected AbstractEventLogItem(T value) {
        value(value);
    }

    public EventLogId eventLogId() {
        return eventLog() == null ? null : eventLog().eventLogId();
    }

    public LocalDate occuredDate() {
        return occuredOn() == null ? null : occuredOn().occuredDate();
    }

    public LocalTime occuredTime() {
        return occuredOn() == null ? null : occuredOn().occuredTime();
    }

    public void commitToEventLog(EventLogId eventLogId, LocalDateTime when, EntityRepository entityRepository) {
        checkNotNull(eventLogId, argNotBeNull("eventLogId"));
        checkNotNull(eventLogId.id(), argNotBeNull("eventLogId.id"));
        checkNotNull(when, argNotBeNull("when"));
        checkNotNull(entityRepository, argNotBeNull("entityRepository"));

        EventLog eventLog = entityRepository.findRequired(EventLog.class, eventLogId);
        eventLog(eventLog).occuredOn(new SeparateDateTime(when));
        entityRepository.add(this);

        eventPublisher().publish(new EventLogItemCommitedToEventLogEvent(eventLogId, EventLogItemId.of(id())));
    }
}
