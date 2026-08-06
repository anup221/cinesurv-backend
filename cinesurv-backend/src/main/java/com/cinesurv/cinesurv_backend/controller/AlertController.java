package com.cinesurv.cinesurv_backend.controller;

import com.cinesurv.cinesurv_backend.model.AlertStats;
import com.cinesurv.cinesurv_backend.model.ThreatAlert;
import com.cinesurv.cinesurv_backend.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    /**
     * Main ingestion endpoint. The Raspberry Pi (or the simulator script)
     * POSTs a JSON payload here whenever a verified threat is detected.
     *
     * Example payload:
     * {
     *   "threatType": "Smartphone",
     *   "seatCoordinate": "G-12",
     *   "hallName": "Hall 1",
     *   "cameraId": "Camera-1",
     *   "confidence": 0.94
     * }
     */
    @PostMapping("/log")
    public ResponseEntity<ThreatAlert> receiveThreatAlert(@RequestBody ThreatAlert incomingAlert) {
        ThreatAlert saved = alertService.logAlert(incomingAlert);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ThreatAlert>> getHistory() {
        return ResponseEntity.ok(alertService.getHistory());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ThreatAlert>> getActiveAlerts() {
        return ResponseEntity.ok(alertService.getActiveAlerts());
    }

    @GetMapping("/stats")
    public ResponseEntity<AlertStats> getStats() {
        return ResponseEntity.ok(alertService.getStats());
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<?> resolveAlert(@PathVariable Long id) {
        try {
            ThreatAlert resolved = alertService.resolveAlert(id);
            return ResponseEntity.ok(resolved);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        alertService.deleteAlert(id);
        return ResponseEntity.noContent().build();
    }
}