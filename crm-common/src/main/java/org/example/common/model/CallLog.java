package org.example.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "call_log")
public class CallLog {

    @Id
    @UuidGenerator
    @Column(name = "call_log_id", nullable = false)
    private UUID id;
    private LocalDateTime callStart;
    private LocalDateTime ringStart;
    private LocalDateTime answerStart;
    private LocalDateTime callEnd;
    private Duration duration;
    private String callerNumber;
    private String destinationNumber;
    @Enumerated(EnumType.STRING)
    private CallResult result;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateTime;
}