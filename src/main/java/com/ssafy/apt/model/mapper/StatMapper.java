package com.ssafy.apt.model.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ssafy.apt.model.AptDto;
import com.ssafy.apt.model.AptStatDto;
import com.ssafy.apt.model.DongStatDto;
import com.ssafy.apt.model.GugunStatDto;

@Mapper
public interface StatMapper {
  public List<GugunStatDto> getGugunAll() throws Exception;
  public List<DongStatDto> getDongStatAll() throws Exception;
  public List<DongStatDto> getDongStatByGugun(String shortDongcode) throws Exception;
  public List<AptStatDto> getAptStatByDong(String shortDongcode) throws Exception;
  public List<AptDto> getAptInfoAll() throws Exception;
  public AptStatDto getAptStat(String aptSeq) throws Exception;

  public int logRegionalQuery(String aptSeq) throws Exception;
}
