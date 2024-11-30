package com.ssafy.apt.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.apt.model.AptDealDto;
import com.ssafy.apt.model.AptDto;
import com.ssafy.apt.model.mapper.AptDealMapper;

@Service
public class AptDealServiceImpl implements AptDealService {

  private final AptDealMapper mapper;

  public AptDealServiceImpl(AptDealMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public List<AptDto> getDealDataByCond(Map<String, String> filterMap) {
    return mapper.getDealDataByCond(filterMap);
  }

  @Override
  public List<AptDealDto> getDealDataAll(String apt_seq) {
    return mapper.getDealDataAll(apt_seq);
  }

  @Override
  public AptDto getAptDataBySeq(String apt_seq) {
    return mapper.getAptDataBySeq(apt_seq);
  }
}
