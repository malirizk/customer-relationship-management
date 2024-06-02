package org.example.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallLogResponse {

    @Schema(description = "The unique identifier of the call log")
    private UUID id;
    @Schema(description = "The start time of the call")
    private LocalDateTime callStart;
    @Schema(description = "The start time when the phone starts ringing")
    private LocalDateTime ringStart;
    @Schema(description = "The start time when the call is answered")
    private LocalDateTime answerStart;
    @Schema(description = "The end time of the call")
    private LocalDateTime callEnd;
    @Schema(description = "The duration of the call in milliseconds")
    private String duration;
    @Schema(description = "The phone number of the caller")
    private String callerNumber;
    @Schema(description = "The phone number of the destination")
    private String destinationNumber;
}
