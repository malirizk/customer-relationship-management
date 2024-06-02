package org.example.telephonyservice.resource;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.CallLogResponse;
import org.example.telephonyservice.service.TelephonyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/crm-telephony-service/v1")
@AllArgsConstructor
public class TelephonyResource {

    private final TelephonyService telephonyService;

    @GetMapping("/transfer-call")
    public Mono<ResponseEntity<CallLogResponse>> createTransferCall() {
        return telephonyService.transferCall();
    }
}
