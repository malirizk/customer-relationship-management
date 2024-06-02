package org.example.callreportservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.callreportservice.dto.CustomerCallsReportResponse;
import org.example.callreportservice.repository.CallReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CallReportService {

    private final CallReportRepository callReportRepository;

    @Transactional(readOnly = true)
    public CustomerCallsReportResponse getCustomerCallsReport(UUID customerId) {
        List<Object[]> result = callReportRepository.getCustomerCallsReport(customerId);
        if (result.isEmpty())
            return new CustomerCallsReportResponse();

        Object[] row = result.get(0);
        return CustomerCallsReportResponse.builder()
                .totalCalls((Integer) row[0])
                .successfulCalls((Integer) row[1])
                .incompleteCalls((Integer) row[2])
                .unmatchedCalls((Integer) row[3])
                .totalDuration((Long) row[4])
                .latestSyncDate((LocalDateTime) row[5])
                .build();
    }


}
