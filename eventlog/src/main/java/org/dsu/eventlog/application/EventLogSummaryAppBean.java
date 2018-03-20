package org.dsu.eventlog.application;

import static java.time.LocalDate.parse;

import static org.dsu.eventlog.domain.common.SpanDate.today;
import static org.dsu.eventlog.domain.model.eventlog.EventLogId.of;
import static org.dsu.eventlog.util.StringPatternFormatter.argNotBeNull;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dsu.eventlog.application.dto.EventLogSummaryDto;
import org.dsu.eventlog.domain.common.SpanDate;
import org.dsu.eventlog.domain.service.EventLogSummary;
import org.dsu.eventlog.domain.service.EventLogSummaryService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Component
@Transactional
@AllArgsConstructor
public class EventLogSummaryAppBean {

    private final EventLogSummaryService eventLogSummaryService;

    public EventLogSummaryDto summary(long eventLogId) {
        return new EventLogSummaryDto(eventLogSummaryService.calcSummary(of(eventLogId), today()));
    }

    public List<EventLogSummaryDto> summaryDivideByDays(long eventLogId, String from, String to) {
        checkNotNull(from, argNotBeNull("from"));
        checkNotNull(to, argNotBeNull("to"));
        
        SpanDate spanDate = new SpanDate(parse(from), parse(to));
        Map<LocalDate, EventLogSummary> summaryByDays = eventLogSummaryService.calcSummaryDivideByDays(of(eventLogId),
                spanDate);
        return summaryByDays.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(this::toDto)
                .collect(Collectors.toList());
    }

    private EventLogSummaryDto toDto(Map.Entry<LocalDate, EventLogSummary> entry) {
        return new EventLogSummaryDto(entry.getKey(), entry.getValue());
    }
}
