package com.ssafy.dashboard.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface QueryMetricsMapper {
  List<Map<String, Object>> getLatestMetrics(int limit);
  List<Map<String, Object>> getRegionalMetrics();
}
