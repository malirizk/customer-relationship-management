package com.example.calllogservice.resource.api;

import org.example.common.swagger.CreatedApiResponse;
import org.example.common.swagger.CustomerIdImplicitParam;
import org.example.common.swagger.DefaultApiResponse;
import org.example.common.swagger.DeletedApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.example.common.dto.CallLogRequest;
import org.example.common.dto.CallLogResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CallLogResourceApi {

    String CUSTOMER_ID = "X-CUSTOMER-ID";

    @Operation(description = "Create a new call log")
    @CreatedApiResponse
    @CustomerIdImplicitParam
    CallLogResponse createCallLog(UUID customerId, CallLogRequest callLogRequest);


    @Operation(description = "Get a call log by ID")
    @DefaultApiResponse
    @CustomerIdImplicitParam
    CallLogResponse getCallLogById(UUID callLogId);


    @Operation(description = "Update a call log by ID")
    @DefaultApiResponse
    @CustomerIdImplicitParam
    CallLogResponse updateCallLog(UUID callLogId, CallLogRequest callLogRequest);


    @Operation(description = "Delete a call log by ID")
    @DeletedApiResponse
    @CustomerIdImplicitParam
    void deleteCallLog(UUID callLogId);
}
