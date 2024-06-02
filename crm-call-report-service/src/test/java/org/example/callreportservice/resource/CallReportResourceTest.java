package org.example.callreportservice.resource;

import org.example.callreportservice.dto.CustomerCallsReportResponse;
import org.example.callreportservice.service.CallReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CallReportResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CallReportService callReportService;

    @Test
    void should_return_customer_call_report() throws Exception {
        UUID customerId = UUID.randomUUID();
        CustomerCallsReportResponse expectedResponse = CustomerCallsReportResponse.builder()
                .totalCalls(10)
                .successfulCalls(8)
                .incompleteCalls(1)
                .unmatchedCalls(1)
                .totalDuration(1000)
                .latestSyncDate(LocalDateTime.now())
                .build();

        given(callReportService.getCustomerCallsReport(customerId)).willReturn(expectedResponse);

        mockMvc.perform(get("/crm-call-report/customer/calls/report")
                        .header("X-CUSTOMER-ID", customerId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCalls").value(expectedResponse.getTotalCalls()))
                .andExpect(jsonPath("$.successfulCalls").value(expectedResponse.getSuccessfulCalls()))
                .andExpect(jsonPath("$.incompleteCalls").value(expectedResponse.getIncompleteCalls()))
                .andExpect(jsonPath("$.unmatchedCalls").value(expectedResponse.getUnmatchedCalls()))
                .andExpect(jsonPath("$.totalDuration").value(expectedResponse.getTotalDuration()))
                .andExpect(jsonPath("$.latestSyncDate").value(expectedResponse.getLatestSyncDate().toString()));
    }
}
