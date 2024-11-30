package com.ssafy.dashboard.model.service;
import org.springframework.stereotype.Service;
import com.ssafy.dashboard.model.AtomicApiMetric;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ApiMetricsService {
    private final ConcurrentHashMap<String, AtomicApiMetric> metrics = new ConcurrentHashMap<>();

    // 데이터 기록
    public void record(String endpoint, String method, long responseTime) {
        String key = endpoint + "::" + method;
        metrics.compute(key, (k, metric) -> {
            if (metric == null) {
                return new AtomicApiMetric(responseTime, 1, 5);
            }
            metric.update(responseTime);
            return metric;
        });
    }

    // 모든 메트릭 조회
    public Map<String, AtomicApiMetric> getMetrics() {
        return metrics;
    }

    // 특정 메트릭 조회
    public AtomicApiMetric getMetric(String endpoint, String method) {
        return metrics.get(endpoint + "::" + method);
    }
}