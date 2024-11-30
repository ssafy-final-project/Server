package com.ssafy.env.model.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.env.model.NearInfoDto;

public interface NearInfoService {
  void registInfo(List<NearInfoDto> nearInfoList);

  List<NearInfoDto> getAllInfo();

  List<NearInfoDto> getInfoByDistrict(String district);
}
