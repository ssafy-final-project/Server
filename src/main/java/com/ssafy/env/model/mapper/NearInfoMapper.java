package com.ssafy.env.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.env.model.NearInfoDto;

@Mapper
public interface NearInfoMapper {
  // 파라미터명 infoList -> nearInfoList
  // 변수명 바꾸면 안됌! 아마도 mybatis nearinfo.xml랑 연관되어있음
  void registInfo(List<NearInfoDto> nearInfoList);

  List<NearInfoDto> getAllInfo();

  List<NearInfoDto> getInfoByDistrict(String district);
}
