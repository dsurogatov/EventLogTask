package org.dsu.eventlog.domain.service;

import org.apache.commons.lang3.tuple.Pair;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class EventLogSummary {

    private final Pair<Long, Long> pair;
    
    EventLogSummary(long countItems, long sumValues) {
        pair = Pair.of(countItems, sumValues);
    }
    
    public long countItems() {
        return pair.getLeft();
    }
    
    public long sumValues() {
        return pair.getRight();
    }
}
