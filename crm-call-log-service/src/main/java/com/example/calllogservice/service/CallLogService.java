package com.example.calllogservice.service;

import com.example.calllogservice.exception.ErrorCode;
import com.example.calllogservice.exception.ServiceException;
import com.example.calllogservice.mapper.CallLogMapper;
import com.example.calllogservice.repository.CallLogRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.CallLogRequest;
import org.example.common.dto.CallLogResponse;
import org.example.common.model.CallLog;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CallLogService {

    private final CallLogRepository callLogRepository;
    private final CallLogMapper callLogMapper;

    public CallLogResponse createCallLog(final UUID customerId, final CallLogRequest callLogRequest) {
        // TODO: Call CRM customer service to validate customerId. If the customer does not exist, throw CustomerNotFoundException.
        // TODO: Check if the customer has another open call. If so, throw CanNotOpenCall.
        if (customerId == null) {
            throw new ServiceException(ErrorCode.CUSTOMER_ID_NOT_FOUND);
        }
        log.debug("Create call log for customer id {}", customerId);
        CallLog callLog = callLogMapper.toCallLog(callLogRequest);
        calculateCallDuration(callLog);
        callLogRepository.save(callLog);
        return callLogMapper.toCallLogResponse(callLog);
    }

    public CallLogResponse getCallLogById(final UUID callLogId) {
        return callLogRepository
                .findById(callLogId)
                .map(callLogMapper::toCallLogResponse)
                .orElseThrow(() -> new ServiceException(ErrorCode.CALL_LOG_NOT_FOUND, new Object[]{callLogId}));
    }

    public CallLogResponse updateCallLog(final UUID callLogId, final CallLogRequest callLogRequest) {
        // TODO: callEnd, ringStart shouldn't be before callStart
        Optional<CallLog> callLogOptional = callLogRepository.findById(callLogId);
        if (callLogOptional.isEmpty()) {
            throw new ServiceException(ErrorCode.CALL_LOG_NOT_FOUND, new Object[]{callLogId});
        }

        log.debug("Update call log for customer id {}", callLogId);
        CallLog callLog = callLogOptional.get();
        callLogMapper.updateCallLogFromCallLogRequest(callLogRequest, callLog);
        calculateCallDuration(callLog);
        callLogRepository.save(callLog);
        return callLogMapper.toCallLogResponse(callLog);
    }

    public void deleteCallLog(final UUID callLogId) {
        if (!callLogRepository.existsById(callLogId)) {
            throw new ServiceException(ErrorCode.CALL_LOG_NOT_FOUND, new Object[]{callLogId});
        }
        log.debug("Delete call log for customer id {}", callLogId);
        callLogRepository.deleteById(callLogId);
    }

    private void calculateCallDuration(CallLog callLog) {
        if (!Objects.isNull(callLog.getCallStart()) && !Objects.isNull(callLog.getCallEnd())) {
            callLog.setDuration(Duration.between(callLog.getCallStart(), callLog.getCallEnd()));
        }
    }

}
