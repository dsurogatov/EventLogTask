package org.dsu.eventlog.domain.model.common;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Embeddable
@Accessors(fluent = true)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(includeFieldNames = true)
public class SeparateDateTime {

    @Basic
    private LocalDate occuredDate;

    @Basic
    private LocalTime occuredTime;

    public SeparateDateTime(LocalDateTime occuredOn) {
        checkNotNull(occuredOn, "The argument 'occuredOn' must not be null.");

        occuredDate(occuredOn.toLocalDate());
        occuredTime(occuredOn.toLocalTime());
    }
}
