package org.example.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallLogRequest {

    @NotNull(message = "${validation.callStart.notNull}")
    @Schema(description = "The start time of the call")
    private LocalDateTime callStart;
    @Schema(description = "The start time when the phone starts ringing")
    private LocalDateTime ringStart;
    @Schema(description = "The start time when the call is answered")
    private LocalDateTime answerStart;
    @Schema(description = "The end time of the call")
    private LocalDateTime callEnd;
    @NotNull
    @Pattern(regexp="^\\+[1-9]\\d{1,14}$", message="${validation.invalid.phone.number}")
    @Schema(description = "The phone number of the caller", example = "+31657371183")
    private String callerNumber;
    @NotNull
    @Pattern(regexp="^\\+[1-9]\\d{1,14}$", message="${validation.invalid.phone.number}")
    @Schema(description = "The phone number of the destination", example = "+31657371183")
    private String destinationNumber;
}
