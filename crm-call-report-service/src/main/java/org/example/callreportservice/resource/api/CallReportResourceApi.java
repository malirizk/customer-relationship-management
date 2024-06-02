package org.example.callreportservice.resource.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.example.callreportservice.dto.CustomerCallsReportResponse;
import org.example.common.swagger.CustomerIdImplicitParam;
import org.example.common.swagger.DefaultApiResponse;

import java.util.UUID;

public interface CallReportResourceApi {

    String CUSTOMER_ID = "X-CUSTOMER-ID";

    @Operation(summary = "Get Customer Call Report", description = "Get a detailed report on the call data of a customer.")
    @DefaultApiResponse
    @CustomerIdImplicitParam
    CustomerCallsReportResponse getCustomerCallReport(@Parameter(description = "Customer ID", required = true) UUID customerId);
}
