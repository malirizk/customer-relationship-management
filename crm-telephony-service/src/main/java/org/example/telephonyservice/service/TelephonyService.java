package org.example.telephonyservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.CallLogRequest;
import org.example.common.dto.CallLogResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TelephonyService {

    private final WebClient callLogApiClient;
    public static final String CUSTOMER_ID_HEADER = "X-CUSTOMER-ID";


    public Mono<ResponseEntity<CallLogResponse>> transferCall() {
        final LocalDateTime now = LocalDateTime.now();
        CallLogRequest callLogRequest = CallLogRequest.builder()
                .callStart(now)
                .ringStart(now.plusSeconds(1))
                .answerStart(now.plusSeconds(3))
                .callerNumber("67892341")
                .destinationNumber("34568980")
                .callEnd(now.plusMinutes(2))
                .build();

        return callLogApiClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .header(CUSTOMER_ID_HEADER, UUID.randomUUID().toString())
                .body(Mono.just(callLogRequest), CallLogRequest.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handleClientError(clientResponse))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> handleServerError(clientResponse))
                .toEntity(CallLogResponse.class);

    }

    private Mono<? extends Throwable> handleClientError(ClientResponse clientResponse) {
        log.error("Client error occurred");
        return Mono.error(new RuntimeException("Client error occurred"));
    }

    private Mono<? extends Throwable> handleServerError(ClientResponse clientResponse) {
        log.error("Server error occurred");
        return Mono.error(new RuntimeException("Server error occurred"));
    }
}
