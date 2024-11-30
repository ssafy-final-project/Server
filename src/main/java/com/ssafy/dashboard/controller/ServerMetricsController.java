package com.ssafy.dashboard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.dashboard.model.AtomicApiMetric;
import com.ssafy.dashboard.model.service.ApiMetricsService;
import com.ssafy.dashboard.model.service.HikariCPMetricsService;
import com.ssafy.dashboard.model.service.QueryMetricsService;
import com.ssafy.dashboard.model.service.ServerMetricsService;

@RestController
@RequestMapping("/dashboard")
public class ServerMetricsController {

  @Autowired
  private ServerMetricsService serverMetricsService;

  @Autowired
  private QueryMetricsService queryMetricsService;
  
  @Autowired
  private HikariCPMetricsService hikariCPMetricsService;

  @Autowired
  private ApiMetricsService apiMetricsService;

  @GetMapping("/metrics/db")
  public ResponseEntity<List<Map<String, Object>>> getLatestMetrics(
          @RequestParam(defaultValue = "10") int limit) {
      return ResponseEntity.ok(queryMetricsService.getLatestMetrics(limit));
  }
  
  @GetMapping("/metrics/server")
  public ResponseEntity<Map<String, Object>> getServerMetrics() {
    Map<String, Object> metrics = serverMetricsService.collectMetrics();
    return ResponseEntity.ok(metrics);
  }
  
  @GetMapping("/metrics/hikari")
  public ResponseEntity<Map<String, Object>> getHikariMetrics() {
    Map<String, Object> metrics = hikariCPMetricsService.collectHikariMetrics();
    return ResponseEntity.ok(metrics);
  }
  
  @GetMapping("/metrics/regional")
  public ResponseEntity<List<Map<String, Object>>> getRegionalMetrics() {
    List<Map<String, Object>> metrics = queryMetricsService.getRegionalMetrics();
    return ResponseEntity.ok(metrics);
  }

  @GetMapping("/metrics/endpoint")
  public List<Map<String, Object>> getEndpointMetrics() {
      return apiMetricsService.getMetrics().entrySet().stream()
          .map(entry -> {
              String[] keyParts = entry.getKey().split("::");
              String endpoint = keyParts[0];
              String method = keyParts[1];
              AtomicApiMetric metric = entry.getValue();

              Map<String, Object> result = new HashMap<>();
              result.put("endpoint", endpoint);
              result.put("method", method);
              result.put("averageResponseTime", metric.getAverageResponseTime());
              result.put("requestCount", metric.getRequestCount());

              return result;
          })
          .collect(Collectors.toList());
  }
}
