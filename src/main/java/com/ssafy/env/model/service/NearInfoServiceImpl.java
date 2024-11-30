package com.ssafy.env.model.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import com.ssafy.env.model.AirPollutionDto;
import com.ssafy.env.model.NearInfoDto;
import com.ssafy.env.model.mapper.AirPollutionMapper;
import com.ssafy.env.model.mapper.NearInfoMapper;

@Service
public class NearInfoServiceImpl implements NearInfoService {

  private final NearInfoMapper mapper;

  public NearInfoServiceImpl(NearInfoMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void registInfo(List<NearInfoDto> nearInfoList) {
    mapper.registInfo(nearInfoList);
  }

  @Override
  public List<NearInfoDto> getAllInfo() {
    return mapper.getAllInfo();
  }

  @Override
  public List<NearInfoDto> getInfoByDistrict(String district) {
    return mapper.getInfoByDistrict(district);
  }
}
