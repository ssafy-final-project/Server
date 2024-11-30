package com.ssafy.dashboard.model.service;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ServerMetricsService {

    public Map<String, Object> collectMetrics() {
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> metrics = new HashMap<>();
        
        metrics.put("uptime", ManagementFactory.getRuntimeMXBean().getUptime());
        metrics.put("totalMemory", runtime.totalMemory());
        metrics.put("freeMemory", runtime.freeMemory());
        metrics.put("usedMemory", runtime.totalMemory() - runtime.freeMemory());
        
        return metrics;
    }
}
