package com.example.calllogservice.resource;

import com.example.calllogservice.BaseResourceIT;
import org.example.common.model.CallLog;
import com.example.calllogservice.repository.CallLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.CallLogRequest;
import org.example.common.dto.CallLogResponse;
import org.example.common.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@Slf4j
class CallLogResourceIT extends BaseResourceIT {

    @Autowired
    private CallLogRepository callLogRepository;

    public static final String CALL_LOG_URL = "/crm-call-log-service/v1/call-logs";
    public static final String CUSTOMER_ID_HEADER = "X-CUSTOMER-ID";

    final LocalDateTime now = LocalDateTime.now();
    final CallLogRequest callLogRequest = CallLogRequest.builder()
            .callStart(now)
            .ringStart(now.plusSeconds(1))
            .answerStart(now.plusSeconds(5))
            .callEnd(now.plusMinutes(3))
            .callerNumber("+31657371183")
            .destinationNumber("+31657371184")
            .build();
    final CallLog callLog = CallLog.builder()
            .callStart(now)
            .ringStart(now.plusSeconds(1))
            .answerStart(now.plusSeconds(5))
            .callEnd(now.plusMinutes(3))
            .callerNumber("+31657371183")
            .destinationNumber("+31657371184")
            .build();

    @Test
    void should_return_created_call_log_when_creating_call_log() {
        getWebTestClient()
                .post()
                .uri(CALL_LOG_URL)
                .header(CUSTOMER_ID_HEADER, UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(callLogRequest))
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isCreated()
                .expectBody(CallLogResponse.class);
    }

    @Test
    void should_return_bad_request_when_creating_call_log_without_customer_id() {
        getWebTestClient()
                .post()
                .uri(CALL_LOG_URL)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(callLogRequest))
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isBadRequest()
                .expectBody(ErrorResponse.class);
    }

    @Test
    void should_return_not_found_when_call_log_not_found() {
        getWebTestClient()
                .get()
                .uri(CALL_LOG_URL + UUID.randomUUID().toString())
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void should_return_call_log_when_getting_call_log_by_id() {
        callLogRepository.save(callLog);

        getWebTestClient().get()
                .uri(CALL_LOG_URL + "/" + callLog.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(res -> log.debug("Response: {}", res))
                .jsonPath("$.id").isEqualTo(callLog.getId().toString())
                .jsonPath("$.callStart").isEqualTo(callLog.getCallStart().toString())
                .jsonPath("$.callEnd").isEqualTo(callLog.getCallEnd().toString())
                .jsonPath("$.callerNumber").isEqualTo(callLog.getCallerNumber());
    }

    @Test
    void should_return_updated_call_log_when_updating_call_log() {
        callLogRepository.save(callLog);

        final CallLogRequest callLogRequest = CallLogRequest.builder()
                .callEnd(now.plusMinutes(5)).build();

        getWebTestClient().put()
                .uri(CALL_LOG_URL + "/" + callLog.getId())
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(callLogRequest))
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(res -> log.debug("Response: {}", res))
                .jsonPath("$.id").isEqualTo(callLog.getId().toString())
                .jsonPath("$.callEnd").isEqualTo(callLogRequest.getCallEnd().toString())
                .jsonPath("$.callerNumber").isEqualTo(callLog.getCallerNumber());
    }

    @Test
    void should_return_no_content_when_deleting_call_log() {
        callLogRepository.save(callLog);

        getWebTestClient().delete()
                .uri(CALL_LOG_URL + "/" + callLog.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNoContent();

        assertThat(callLogRepository.findById(callLog.getId()).orElse(null), is(nullValue()));
    }
}
