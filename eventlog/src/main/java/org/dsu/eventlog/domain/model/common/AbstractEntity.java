package org.dsu.eventlog.domain.model.common;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.dsu.eventlog.domain.event.EventPublisher;
import org.dsu.eventlog.infrastructura.event.PublisherProvider;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@MappedSuperclass
@Accessors(fluent = true)
@ToString(includeFieldNames = true)
public abstract class AbstractEntity {

    private static final int GID_LENGTH = 36;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    @Getter
    protected Long id;

    @Column(name = "gid", updatable = false, nullable = false, length = GID_LENGTH)
    @Getter
    protected String gid = UUID.randomUUID().toString();

    @Transient
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private EventPublisher eventPublisher = PublisherProvider.eventPublisher();

}
