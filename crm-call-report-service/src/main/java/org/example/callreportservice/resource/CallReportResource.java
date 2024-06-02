package org.example.callreportservice.resource;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.callreportservice.dto.CustomerCallsReportResponse;
import org.example.callreportservice.resource.api.CallReportResourceApi;
import org.example.callreportservice.service.CallReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
public class CallReportResource implements CallReportResourceApi {

    private final CallReportService callReportService;

    @GetMapping("/crm-call-report/customer/calls/report")
    public CustomerCallsReportResponse getCustomerCallReport(final @RequestHeader(CUSTOMER_ID) UUID customerId) {
        return callReportService.getCustomerCallsReport(customerId);
    }
}
