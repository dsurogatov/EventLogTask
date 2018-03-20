package org.dsu.eventlog.presentation;

import java.util.List;

import org.dsu.eventlog.application.EventLogAppBean;
import org.dsu.eventlog.application.dto.RefDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/eventLog")
@AllArgsConstructor
public class EventLogController {

    private final EventLogAppBean eventLogAppBean;

    @RequestMapping
    public List<RefDto> list() {
        return eventLogAppBean.list();
    }

    @RequestMapping(value = "/{id}")
    public RefDto one(@PathVariable("id") long id) {
        return eventLogAppBean.one(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public long create(@RequestBody RefDto refDto) {
        return eventLogAppBean.create(refDto.name(), refDto.description());
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void update(@RequestBody RefDto refDto) {
        eventLogAppBean.update(refDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable("id") long id) {
        eventLogAppBean.remove(id);
    }
}
