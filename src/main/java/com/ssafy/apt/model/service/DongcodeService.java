package com.ssafy.apt.model.service;

import java.sql.SQLException;
import com.ssafy.apt.model.DongcodeDto;

public interface DongcodeService {
  String findDongcodeByAddress(String address) throws SQLException;
  String findDongcodeByName(String sido_name, String gugun_name, String dong_name) throws Exception;
}
