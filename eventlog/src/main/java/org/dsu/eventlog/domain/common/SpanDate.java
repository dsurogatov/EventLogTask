package org.dsu.eventlog.domain.common;

import static java.time.LocalDate.now;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public final class SpanDate {

    public static SpanDate today() {
        return new SpanDate(now(), now());
    }

    public SpanDate(LocalDate begin, LocalDate end) {
        checkNotNull(begin, argNotBeNull("begin"));
        checkNotNull(end, argNotBeNull("end"));
        checkState(end.isAfter(begin) || end.isEqual(begin),
                "The argument 'end' should be less or equal to 'begin' argument.");

        begin(begin).end(end);
    }

    private LocalDate begin;
    private LocalDate end;
}
