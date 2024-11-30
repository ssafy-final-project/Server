package com.ssafy.env.model.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.env.model.TrashDto;

public interface TrashService {
  List<TrashDto> getTrashInfo(String dongcode) throws SQLException;
}
