package com.ssafy.dashboard.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.dashboard.model.mapper.QueryMetricsMapper;
import java.util.List;
import java.util.Map;

@Service
public class QueryMetricsService {

  @Autowired
  private QueryMetricsMapper queryMetricsMapper;

  public List<Map<String, Object>> getLatestMetrics(int limit) {
    return queryMetricsMapper.getLatestMetrics(limit);
  }
  

  public List<Map<String, Object>> getRegionalMetrics() {
    return queryMetricsMapper.getRegionalMetrics();
  }
}
