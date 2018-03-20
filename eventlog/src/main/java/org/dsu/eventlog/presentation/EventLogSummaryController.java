package org.dsu.eventlog.presentation;

import java.util.List;

import org.dsu.eventlog.application.EventLogSummaryAppBean;
import org.dsu.eventlog.application.dto.EventLogSummaryDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/eventLog/summary")
@AllArgsConstructor
public class EventLogSummaryController {

    private final EventLogSummaryAppBean eventLogSummaryAppBean;

    @RequestMapping(value = "/{eventLogId}")
    public EventLogSummaryDto summary(@PathVariable("eventLogId") long eventLogId) {
        return eventLogSummaryAppBean.summary(eventLogId);
    }

    @RequestMapping(value = "/report")
    public List<EventLogSummaryDto> summaryDivideByDays(@RequestParam("eventLogId") long eventLogId,
            @RequestParam("from") String from, @RequestParam("to") String to) {
        return eventLogSummaryAppBean.summaryDivideByDays(eventLogId, from, to);
    }
}
