package com.cinesurv.cinesurv_backend.service;

import com.cinesurv.cinesurv_backend.model.AlertStats;
import com.cinesurv.cinesurv_backend.model.ThreatAlert;
import com.cinesurv.cinesurv_backend.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final SimpMessagingTemplate webSocket;

    @Autowired
    public AlertService(AlertRepository alertRepository, SimpMessagingTemplate webSocket) {
        this.alertRepository = alertRepository;
        this.webSocket = webSocket;
    }

    /**
     * Receives a new detection from the edge device (or simulator),
     * persists it, and instantly broadcasts it to any connected dashboards.
     */
    public ThreatAlert logAlert(ThreatAlert incoming) {
        if (incoming.getStatus() == null) {
            incoming.setStatus("ACTIVE");
        }
        ThreatAlert saved = alertRepository.save(incoming);

        // Push to every subscriber of /topic/live-threats in real time
        webSocket.convertAndSend("/topic/live-threats", saved);

        return saved;
    }

    public List<ThreatAlert> getHistory() {
        return alertRepository.findAllByOrderByDetectedAtDesc();
    }

    public List<ThreatAlert> getActiveAlerts() {
        return alertRepository.findByStatusOrderByDetectedAtDesc("ACTIVE");
    }

    public ThreatAlert resolveAlert(Long id) {
        ThreatAlert alert = alertRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Alert not found: " + id));

        alert.setStatus("RESOLVED");
        alert.setResolvedAt(LocalDateTime.now());
        ThreatAlert saved = alertRepository.save(alert);

        // Let the dashboard know this alert was cleared, in real time
        webSocket.convertAndSend("/topic/alert-updates", saved);

        return saved;
    }

    public void deleteAlert(Long id) {
        alertRepository.deleteById(id);
    }

    public AlertStats getStats() {
        long total = alertRepository.count();
        long active = alertRepository.countByStatus("ACTIVE");
        long resolved = alertRepository.countByStatus("RESOLVED");

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        long today = alertRepository.countByDetectedAtBetween(startOfDay, endOfDay);

        return new AlertStats(total, active, resolved, today);
    }
}