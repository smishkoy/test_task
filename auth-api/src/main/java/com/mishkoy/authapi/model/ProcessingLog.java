package com.mishkoy.authapi.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "processing_log")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String requestData;
    private String responseData;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}