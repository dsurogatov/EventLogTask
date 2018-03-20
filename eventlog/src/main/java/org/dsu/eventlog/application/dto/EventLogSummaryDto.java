package org.dsu.eventlog.application.dto;

import java.time.LocalDate;

import org.dsu.eventlog.domain.service.EventLogSummary;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class EventLogSummaryDto {

    private long countItems;
    private long sumValues;
    private String date;

    public EventLogSummaryDto(EventLogSummary eventLogSummary) {
        countItems = eventLogSummary.countItems();
        sumValues = eventLogSummary.sumValues();
    }

    public EventLogSummaryDto(LocalDate date, EventLogSummary eventLogSummary) {
        this(eventLogSummary);
        this.date = date.toString();
    }

}
