package com.cinesurv.cinesurv_backend.repository;

import com.cinesurv.cinesurv_backend.model.ThreatAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertRepository extends JpaRepository<ThreatAlert, Long> {

    List<ThreatAlert> findAllByOrderByDetectedAtDesc();

    List<ThreatAlert> findByStatusOrderByDetectedAtDesc(String status);

    long countByStatus(String status);

    long countByDetectedAtBetween(LocalDateTime start, LocalDateTime end);
}