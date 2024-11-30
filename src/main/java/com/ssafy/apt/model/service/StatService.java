package com.ssafy.apt.model.service;

import java.util.List;
import com.ssafy.apt.model.AptDto;
import com.ssafy.apt.model.AptStatDto;
import com.ssafy.apt.model.DongStatDto;
import com.ssafy.apt.model.GugunStatDto;

public interface StatService {
  public List<GugunStatDto> getGugunAll() throws Exception;
  public List<DongStatDto> getDongStatAll() throws Exception;
  public List<DongStatDto> getDongStatByGugun(String shortDongcode) throws Exception;
  public List<AptStatDto> getAptStatByDong(String shortDongcode) throws Exception;
  public List<AptDto> getAptInfoAll() throws Exception;
  public AptStatDto getAptStat(String aptSeq) throws Exception;
}
