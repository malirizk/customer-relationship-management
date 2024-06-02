package com.example.calllogservice.resource;

import com.example.calllogservice.resource.api.CallLogResourceApi;
import com.example.calllogservice.service.CallLogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.CallLogRequest;
import org.example.common.dto.CallLogResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController()
@RequestMapping("/crm-call-log-service/v1/call-logs")
@AllArgsConstructor
public class CallLogResource implements CallLogResourceApi {

    private final CallLogService callLogService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CallLogResponse createCallLog(final @RequestHeader(CUSTOMER_ID) UUID customerId,
                                         final @RequestBody CallLogRequest callLogRequest) {
        return callLogService.createCallLog(customerId, callLogRequest);
    }

    @GetMapping("/{id}")
    public CallLogResponse getCallLogById(final @PathVariable("id") UUID callLogId) {
        return callLogService.getCallLogById(callLogId);
    }

    @PutMapping("/{id}")
    public CallLogResponse updateCallLog(final @PathVariable("id") UUID callLogId,
                                         final @Valid @RequestBody CallLogRequest callLogRequest) {
        return callLogService.updateCallLog(callLogId, callLogRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCallLog(final @PathVariable("id") UUID callLogId) {
        callLogService.deleteCallLog(callLogId);
    }
}
