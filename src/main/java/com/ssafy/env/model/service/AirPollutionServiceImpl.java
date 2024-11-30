package com.ssafy.env.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import com.ssafy.apt.model.mapper.AptDealMapper;
import com.ssafy.env.model.AirPollutionDto;
import com.ssafy.env.model.mapper.AirPollutionMapper;

@Service
public class AirPollutionServiceImpl implements AirPollutionService {

  private final AirPollutionMapper mapper;

  public AirPollutionServiceImpl(AirPollutionMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public List<AirPollutionDto> getPollutionInfo(Map<String, String> pollutionMap)
      throws SQLException {
    // TODO Auto-generated method stub
    return mapper.getPollutionInfo(pollutionMap);
  }
}
