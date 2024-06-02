package org.example.telephonyservice.resource;

import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.CallLogResponse;
import org.example.telephonyservice.service.TelephonyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@Slf4j
@WebFluxTest(TelephonyResource.class)
class TelephonyResourceTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TelephonyService telephonyService;

    @BeforeEach
    void setUp() {
        when(telephonyService.transferCall())
                .thenReturn(Mono.just(ResponseEntity.status(HttpStatus.OK).body(new CallLogResponse())));
    }

    @Test
    void should_return_ok_when_creating_transfer_call() {
        webTestClient.get()
                .uri("/crm-telephony-service/v1/transfer-call")
                .exchange()
                .expectStatus().isOk();
    }
}
