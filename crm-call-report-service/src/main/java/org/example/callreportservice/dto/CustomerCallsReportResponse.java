package org.example.callreportservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCallsReportResponse {

    @Schema(description = "Total number of calls")
    private int totalCalls;
    @Schema(description = "Total number of successful calls")
    private int successfulCalls;
    @Schema(description = "Total number of incomplete calls")
    private int incompleteCalls;
    @Schema(description = "Total number of unmatched calls")
    private int unmatchedCalls;
    @Schema(description = "Total duration of calls")
    private long totalDuration;
    @Schema(description = "Latest synchronization date")
    private LocalDateTime latestSyncDate;

}
