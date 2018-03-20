package org.dsu.eventlog.integration;

import static java.time.LocalDate.now;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.dsu.eventlog.application.dto.EventLogSummaryDto;
import org.dsu.eventlog.application.dto.RefDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EventLogItemTests {

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private long eventLogId;

    @Before
    public void setUp() {
        HttpEntity<RefDto> request = new HttpEntity<>(new RefDto().name(NAME).description(DESCRIPTION));
        eventLogId = restTemplate.postForObject(url("eventLog"), request, Long.class);
    }

    @After
    public void down() {
        restTemplate.delete(url("eventLog/" + eventLogId));
    }

    @Test
    public void whenGetEventLogs_ThenReturnList() {
        ResponseEntity<List<RefDto>> response = restTemplate.exchange(url("eventLog"), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<RefDto>>() {
                });
        List<RefDto> dtos = response.getBody();
        assertThat(dtos, hasSize(1));
        assertThat(dtos.get(0), is(new RefDto(eventLogId, NAME, DESCRIPTION)));
    }

    private String url(String path) {
        return "http://localhost:" + port + "/" + path;
    }

    @Test
    public void whenAddNewEventLogItem_ThenItsAdded() {
        postEventLogValue(eventLogId, 10);

        EventLogSummaryDto summary = restTemplate.getForObject(url("eventLog/summary/" + eventLogId),
                EventLogSummaryDto.class);
        assertThat(summary.getCountItems(), is(1L));
        assertThat(summary.getSumValues(), is(10L));

        ResponseEntity<List<EventLogSummaryDto>> response = restTemplate.exchange(
                url("eventLog/summary/report?eventLogId=" + eventLogId + "&from=" + now().toString() + "&to="
                        + now().toString()),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<EventLogSummaryDto>>() {
                });
        List<EventLogSummaryDto> dtos = response.getBody();
        assertThat(dtos, hasSize(1));
        assertThat(dtos.get(0).getDate(), is(now().toString()));
        assertThat(dtos.get(0).getCountItems(), is(1L));
        assertThat(dtos.get(0).getSumValues(), is(10L));
    }

    private void postEventLogValue(long eventLogId, int value) {
        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("eventLogId", String.valueOf(eventLogId));
        params.add("value", String.valueOf(value));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url("eventLogItem"), requestEntity, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}
