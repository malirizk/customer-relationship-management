package com.example.calllogservice.resource;

import com.example.calllogservice.service.CallLogService;
import org.example.common.dto.CallLogRequest;
import org.example.common.dto.CallLogResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CallLogResourceTest {

    @Mock
    private CallLogService callLogService;

    @InjectMocks
    private CallLogResource callLogResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_CreateCallLog_When_ValidRequest() {
        final UUID customerId = UUID.randomUUID();
        final LocalDateTime now = LocalDateTime.now();
        final CallLogRequest callLogRequest = CallLogRequest.builder()
                .callStart(now)
                .ringStart(now.plusSeconds(1))
                .answerStart(now.plusSeconds(5))
                .callEnd(now.plusMinutes(3))
                .callerNumber("+31657371183")
                .destinationNumber("+31657371184")
                .build();
        final CallLogResponse expectedResponse = CallLogResponse.builder()
                .callStart(now)
                .ringStart(now.plusSeconds(1))
                .answerStart(now.plusSeconds(5))
                .callEnd(now.plusMinutes(3))
                .duration("180000")
                .callerNumber("+31657371183")
                .destinationNumber("+31657371184")
                .build();

        when(callLogService.createCallLog(customerId, callLogRequest)).thenReturn(expectedResponse);

        CallLogResponse actualResponse = callLogResource.createCallLog(customerId, callLogRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void should_GetCallLogById_When_ValidId() {
        final UUID callLogId = UUID.randomUUID();
        final LocalDateTime now = LocalDateTime.now();
        final CallLogResponse expectedResponse = CallLogResponse.builder()
                .callStart(now)
                .ringStart(now.plusSeconds(1))
                .answerStart(now.plusSeconds(5))
                .callEnd(now.plusMinutes(3))
                .duration("180000")
                .callerNumber("+31657371183")
                .destinationNumber("+31657371184")
                .build();

        when(callLogService.getCallLogById(callLogId)).thenReturn(expectedResponse);

        CallLogResponse actualResponse = callLogResource.getCallLogById(callLogId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void should_UpdateCallLog_When_ValidRequest() {
        final UUID callLogId = UUID.randomUUID();
        final LocalDateTime now = LocalDateTime.now();
        final CallLogRequest callLogRequest = CallLogRequest.builder()
                .callEnd(now.plusMinutes(5))
                .build();
        final CallLogResponse expectedResponse = CallLogResponse.builder()
                .callStart(now)
                .ringStart(now.plusSeconds(1))
                .answerStart(now.plusSeconds(5))
                .callEnd(now.plusMinutes(5))
                .duration("180000")
                .callerNumber("+31657371183")
                .destinationNumber("+31657371184")
                .build();

        when(callLogService.updateCallLog(callLogId, callLogRequest)).thenReturn(expectedResponse);

        CallLogResponse actualResponse = callLogResource.updateCallLog(callLogId, callLogRequest);

        assertEquals(expectedResponse, actualResponse);
    }
}
