package com.ssafy.apt.model.service;

import java.sql.SQLException;
import org.springframework.stereotype.Service;
import com.ssafy.apt.model.mapper.DongcodeMapper;

@Service
public class DongcodeServiceImpl implements DongcodeService {

  DongcodeMapper mapper;
  
  public DongcodeServiceImpl(DongcodeMapper mapper) {
    this.mapper = mapper;
  }


  @Override
  public String findDongcodeByAddress(String address) throws SQLException {
    return mapper.findDongcodeByAddress(address);
  }

  @Override
  public String findDongcodeByName(String sido_name, String gugun_name, String dong_name)
      throws Exception {
    return mapper.findDongcodeByName(sido_name, gugun_name, dong_name);
  }
}
