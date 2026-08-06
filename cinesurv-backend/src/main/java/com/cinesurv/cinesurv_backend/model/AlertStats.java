package com.cinesurv.cinesurv_backend.model;

public class AlertStats {

    private long totalAlerts;
    private long activeAlerts;
    private long resolvedAlerts;
    private long detectionsToday;

    public AlertStats(long totalAlerts, long activeAlerts, long resolvedAlerts, long detectionsToday) {
        this.totalAlerts = totalAlerts;
        this.activeAlerts = activeAlerts;
        this.resolvedAlerts = resolvedAlerts;
        this.detectionsToday = detectionsToday;
    }

    public long getTotalAlerts() { return totalAlerts; }
    public long getActiveAlerts() { return activeAlerts; }
    public long getResolvedAlerts() { return resolvedAlerts; }
    public long getDetectionsToday() { return detectionsToday; }
}