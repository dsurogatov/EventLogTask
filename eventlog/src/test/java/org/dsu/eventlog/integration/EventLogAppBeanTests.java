package org.dsu.eventlog.integration;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.dsu.eventlog.application.EventLogAppBean;
import org.dsu.eventlog.application.EventLogItemAppBean;
import org.dsu.eventlog.application.EventLogSummaryAppBean;
import org.dsu.eventlog.application.dto.EventLogSummaryDto;
import org.dsu.eventlog.application.dto.RefDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventLogAppBeanTests {
    
    @Autowired
    private EventLogAppBean eventLogAppBean;

    @Autowired
    private EventLogSummaryAppBean eventLogSummaryAppBean;

    @Autowired
    private EventLogItemAppBean eventLogItemAppBean;
    
    @Test
    public void whenCreateRemoveEventLog_ThenAddedAndRemoved() {
        long eventLogId1 = eventLogAppBean.create("name", "description");
        long eventLogId2 = eventLogAppBean.create("name2", "description2");
        
        List<RefDto> dtos = eventLogAppBean.list();
        
        assertThat(dtos, hasSize(2));
        assertThat(dtos.get(0), is(new RefDto(eventLogId1, "name", "description")));
        assertThat(dtos.get(1), is(new RefDto(eventLogId2, "name2", "description2")));
        
        eventLogAppBean.remove(eventLogId1);
        eventLogAppBean.remove(eventLogId2);
        assertThat(eventLogAppBean.list(), empty());
    }
    
    @Test
    public void whenAddValueToEventLog_ThenLookValueInSummary() {
        long eventLogId1 = eventLogAppBean.create("name", "description");
        
        eventLogItemAppBean.addIntValue(eventLogId1, 10);
        eventLogItemAppBean.addIntValue(eventLogId1, 20);
        
        EventLogSummaryDto eventLogSummaryDto = eventLogSummaryAppBean.summary(eventLogId1);
        assertThat(eventLogSummaryDto.getCountItems(), is(2L));
        assertThat(eventLogSummaryDto.getSumValues(), is(30L));
        
        eventLogAppBean.remove(eventLogId1);
    }
}
