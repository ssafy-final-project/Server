package com.ssafy.apt.model.mapper;

import java.sql.SQLException;
import org.apache.ibatis.annotations.Mapper;
import com.ssafy.apt.model.DongcodeDto;

@Mapper
public interface DongcodeMapper {
  String findDongcodeByAddress(String address) throws SQLException;
  String findDongcodeByName(String sido_name, String gugun_name, String dong_name) throws Exception;
}
