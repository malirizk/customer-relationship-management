package com.example.calllogservice.service;

import com.example.calllogservice.exception.ServiceException;
import com.example.calllogservice.mapper.CallLogMapper;
import com.example.calllogservice.repository.CallLogRepository;
import org.example.common.dto.CallLogRequest;
import org.example.common.dto.CallLogResponse;
import org.example.common.model.CallLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CallLogServiceTest {

    @Mock
    private CallLogRepository callLogRepository;

    @Mock
    private CallLogMapper callLogMapper;

    @InjectMocks
    private CallLogService callLogService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

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
    final CallLog callLog = CallLog.builder()
            .id(UUID.randomUUID())
            .callStart(now)
            .ringStart(now.plusSeconds(1))
            .answerStart(now.plusSeconds(5))
            .callEnd(now.plusMinutes(3))
            .duration(Duration.of(1800000, ChronoUnit.NANOS))
            .callerNumber("+31657371183")
            .destinationNumber("+31657371184")
            .build();
    final CallLogResponse expectedResponse = CallLogResponse.builder()
            .callStart(now)
            .ringStart(now.plusSeconds(1))
            .answerStart(now.plusSeconds(5))
            .callEnd(now.plusMinutes(3))
            .callerNumber("+31657371183")
            .destinationNumber("+31657371184")
            .build();

    @Test
    void should_create_call_log_when_valid_customer_id_and_request() {
        when(callLogMapper.toCallLog(callLogRequest)).thenReturn(callLog);

        CallLogResponse expectedResponse = new CallLogResponse();
        when(callLogMapper.toCallLogResponse(callLog)).thenReturn(expectedResponse);
        CallLogResponse actualResponse = callLogService.createCallLog(customerId, callLogRequest);

        verify(callLogRepository).save(callLog);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void should_throw_service_exception_when_null_customer_id() {
        assertThrows(ServiceException.class, () -> callLogService.createCallLog(null, new CallLogRequest()));
    }

    @Test
    void should_return_call_log_when_existing_id() {
        when(callLogRepository.findById(callLog.getId())).thenReturn(Optional.of(callLog));

        CallLogResponse expectedResponse = new CallLogResponse();
        when(callLogMapper.toCallLogResponse(callLog)).thenReturn(expectedResponse);
        CallLogResponse actualResponse = callLogService.getCallLogById(callLog.getId());

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void should_throw_service_exception_when_non_existing_id() {
        when(callLogRepository.findById(callLog.getId())).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> callLogService.getCallLogById(callLog.getId()));
    }

    @Test
    void should_update_call_log_when_existing_id_and_valid_request() {
        when(callLogMapper.toCallLog(callLogRequest)).thenReturn(callLog);
        when(callLogRepository.findById(callLog.getId())).thenReturn(Optional.of(callLog));

        when(callLogMapper.toCallLogResponse(callLog)).thenReturn(expectedResponse);
        CallLogResponse actualResponse = callLogService.updateCallLog(callLog.getId(), callLogRequest);

        verify(callLogRepository).save(callLog);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void should_throw_service_exception_when_non_existing_id_on_update() {
        when(callLogRepository.existsById(callLog.getId())).thenReturn(false);

        assertThrows(ServiceException.class, () -> callLogService.updateCallLog(callLog.getId(), new CallLogRequest()));
    }

    @Test
    void should_delete_call_log_when_existing_id() {
        when(callLogRepository.existsById(callLog.getId())).thenReturn(true);

        callLogService.deleteCallLog(callLog.getId());

        verify(callLogRepository).deleteById(callLog.getId());
    }

    @Test
    void should_throw_service_exception_when_non_existing_id_on_delete() {
        when(callLogRepository.existsById(callLog.getId())).thenReturn(false);

        assertThrows(ServiceException.class, () -> callLogService.deleteCallLog(callLog.getId()));
    }
}
