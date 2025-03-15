package com.timeclock.api.controller;

import com.timeclock.api.commons.FileUtils;
import com.timeclock.api.response.TimeEntryPostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimeEntryControllerIT {

    private static final String URL = "/api/time-entry";

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

    private static HttpEntity<String> buildHttpEntity(String request) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(request, httpHeaders);
    }

}