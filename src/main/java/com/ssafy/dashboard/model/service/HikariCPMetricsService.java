package com.ssafy.dashboard.model.service;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HikariCPMetricsService {

    @Autowired
    private HikariDataSource dataSource;

    public Map<String, Object> collectHikariMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("activeConnections", dataSource.getHikariPoolMXBean().getActiveConnections());
        metrics.put("idleConnections", dataSource.getHikariPoolMXBean().getIdleConnections());
        metrics.put("totalConnections", dataSource.getHikariPoolMXBean().getTotalConnections());
        metrics.put("threadsAwaitingConnection", dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());
        return metrics;
    }
}
