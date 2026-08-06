package com.cinesurv.cinesurv_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "threat_alerts")
public class ThreatAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // e.g. "Smartphone", "Professional Camera"
    @Column(nullable = false)
    private String threatType;

    // e.g. "G-12"
    @Column(nullable = false)
    private String seatCoordinate;

    // e.g. "Hall 1"
    private String hallName = "Hall 1";

    // e.g. "Camera-1"
    private String cameraId = "Camera-1";

    // Model confidence, 0.0 - 1.0
    private Double confidence;

    // ACTIVE -> RESOLVED
    private String status = "ACTIVE";

    // When the detection actually happened (set by edge device / simulator)
    private LocalDateTime detectedAt;

    // When this row was written to the DB (server-side, automatic)
    private LocalDateTime createdAt;

    private LocalDateTime resolvedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.detectedAt == null) {
            this.detectedAt = this.createdAt;
        }
        if (this.status == null) {
            this.status = "ACTIVE";
        }
    }

    // --- Getters and setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getThreatType() { return threatType; }
    public void setThreatType(String threatType) { this.threatType = threatType; }

    public String getSeatCoordinate() { return seatCoordinate; }
    public void setSeatCoordinate(String seatCoordinate) { this.seatCoordinate = seatCoordinate; }

    public String getHallName() { return hallName; }
    public void setHallName(String hallName) { this.hallName = hallName; }

    public String getCameraId() { return cameraId; }
    public void setCameraId(String cameraId) { this.cameraId = cameraId; }

    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDetectedAt() { return detectedAt; }
    public void setDetectedAt(LocalDateTime detectedAt) { this.detectedAt = detectedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }
}