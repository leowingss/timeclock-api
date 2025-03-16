package com.timeclock.api.controller;

import com.timeclock.api.commons.FileUtils;
import com.timeclock.api.response.TimeEntryPostResponse;
import com.timeclock.api.response.TotalWorkedHoursResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimeEntryControllerIT {

    private static final String URL = "/api/time-entry";
    private static final String WORKED_HOURS_URL = "/api/time-entry/worked-hours?date=";
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FileUtils fileUtils;

    @Test
    @DisplayName("POST /api/time-entry should returns TimeEntryResponse with status 201")
    void registerTimeEntry_ReturnsTimeEntryResponse_WhenSuccessful() throws Exception {

        var request = fileUtils.readResourceFile("time/post-request-time-entry-200.json");
        var timeEntry = buildHttpEntity(request);

        var responseEntity = testRestTemplate.exchange(URL, POST, timeEntry, TimeEntryPostResponse.class);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    @DisplayName("GET /api/time-entry/worked-hours should returns worked hours by specific day")
    @Sql("/sql/init_time_entry.sql")
    void getWorkedHours_ReturnsWorkedHours_WhenSuccessful() throws Exception {

        var date = LocalDate.of(2025, 3, 20);

        var responseEntity = testRestTemplate.exchange(WORKED_HOURS_URL + date, GET, null, TotalWorkedHoursResponse.class);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getTotalHours()).isGreaterThan(0);
    }

    private static HttpEntity<String> buildHttpEntity(String request) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(request, httpHeaders);
    }

}