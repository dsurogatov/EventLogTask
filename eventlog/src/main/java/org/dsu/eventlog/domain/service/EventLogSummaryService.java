package org.dsu.eventlog.domain.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.dsu.eventlog.domain.common.SpanDate;
import org.dsu.eventlog.domain.model.eventlog.EventLogId;
import org.dsu.eventlog.domain.model.eventlogitem.EventLogItemIntValue;
import org.dsu.eventlog.domain.model.eventlogitem.EventLogItemRepository;
import org.dsu.eventlog.infrastructura.service.DomainService;

import lombok.AllArgsConstructor;

@DomainService
@AllArgsConstructor
public class EventLogSummaryService {

    private static final SummaryCollector SUMMARY_COLLECTOR = new SummaryCollector();
    
    private final EventLogItemRepository eventLogItemRepository;

    public EventLogSummary calcSummary(EventLogId eventLogId, SpanDate spanDate) {
        checkNotNull(eventLogId, argNotBeNull("eventLogId"));
        checkNotNull(eventLogId.id(), argNotBeNull("eventLogId.id"));
        checkNotNull(spanDate, argNotBeNull("spanDate"));

        List<EventLogItemIntValue> eventLogItems = eventLogItemRepository
                .findByEventLogIdAndOccuredOnOccuredDateBetween(eventLogId.id(), spanDate.begin(), spanDate.end());

        long sumValues = eventLogItems.stream().mapToLong(EventLogItemIntValue::value).reduce(0L, (r, e) -> r + e);
        return new EventLogSummary(eventLogItems.size(), sumValues);
    }

    public Map<LocalDate, EventLogSummary> calcSummaryDivideByDays(EventLogId eventLogId, SpanDate spanDate) {
        checkNotNull(eventLogId, argNotBeNull("eventLogId"));
        checkNotNull(eventLogId.id(), argNotBeNull("eventLogId.id"));
        checkNotNull(spanDate, argNotBeNull("spanDate"));
        
        List<EventLogItemIntValue> eventLogItems = eventLogItemRepository
                .findByEventLogIdAndOccuredOnOccuredDateBetween(eventLogId.id(), spanDate.begin(), spanDate.end());

        return eventLogItems.stream().collect(
                groupingBy(EventLogItemIntValue::occuredDate, mapping(EventLogItemIntValue::value, SUMMARY_COLLECTOR)));
    }
    
    private static class SummaryAccumulator {

        private int count;
        private int sum;

        void accumulate(Integer value) {
            count++;
            sum += value;
        }

        SummaryAccumulator combine(SummaryAccumulator accumulator) {
            count += accumulator.count;
            sum += accumulator.sum;
            return this;
        }

        EventLogSummary toSummary() {
            return new EventLogSummary(count, sum);
        }
    }

    private static class SummaryCollector implements Collector<Integer, SummaryAccumulator, EventLogSummary> {

        @Override
        public Supplier<SummaryAccumulator> supplier() {
            return () -> new SummaryAccumulator();
        }

        @Override
        public BiConsumer<SummaryAccumulator, Integer> accumulator() {
            return SummaryAccumulator::accumulate;
        }

        @Override
        public BinaryOperator<SummaryAccumulator> combiner() {
            return SummaryAccumulator::combine;
        }

        @Override
        public Function<SummaryAccumulator, EventLogSummary> finisher() {
            return SummaryAccumulator::toSummary;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.emptySet();
        }

    }
}
