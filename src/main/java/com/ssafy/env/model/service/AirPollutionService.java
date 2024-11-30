package com.ssafy.env.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.env.model.AirPollutionDto;


public interface AirPollutionService {
  List<AirPollutionDto> getPollutionInfo(Map<String, String> pollutionMap) throws SQLException;
}
