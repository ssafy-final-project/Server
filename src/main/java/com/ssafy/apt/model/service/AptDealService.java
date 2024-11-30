package com.ssafy.apt.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apt.model.AptDealDto;
import com.ssafy.apt.model.AptDto;

public interface AptDealService {

  // Multiple APT Method
  /*
   * filterMap의 key 1. low (가격 하한) 2. high (가격 상한) 3. district_code (지역 정보) 4. year (거래 년도) 원래 low,
   * high, year는 int 타입이였는데 그냥 String으로 다 보내는게 나을듯
   */
  public List<AptDto> getDealDataByCond(Map<String, String> filterMap);

  // Single APT Method
  public List<AptDealDto> getDealDataAll(String apt_seq);
  public AptDto getAptDataBySeq(String apt_seq);
}
