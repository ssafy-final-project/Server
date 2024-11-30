package com.ssafy.env.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import com.ssafy.env.model.AirPollutionDto;


@Mapper
public interface AirPollutionMapper {
  /*
   * trashMap의 key 1. location 2. dongcode
   * 
   * Map으로 바꿔야합니다 인자
   */
  List<AirPollutionDto> getPollutionInfo(Map<String, String> pollutionMap) throws SQLException;

  void registInfo(List<AirPollutionDto> infoList) throws SQLException;
}
