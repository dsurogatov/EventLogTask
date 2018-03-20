package org.dsu.eventlog.presentation;

import org.dsu.eventlog.application.EventLogItemAppBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/eventLogItem")
@AllArgsConstructor
public class EventLogItemController {

    private final EventLogItemAppBean eventLogItemAppBean;

    @RequestMapping(method = RequestMethod.POST)
    public void addIntValue(@RequestParam("eventLogId") long eventLogId, @RequestParam("value") int value) {
        eventLogItemAppBean.addIntValue(eventLogId, value);
    }
}
