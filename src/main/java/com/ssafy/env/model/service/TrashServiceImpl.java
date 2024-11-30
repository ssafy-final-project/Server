package com.ssafy.env.model.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import com.ssafy.env.model.TrashDto;
import com.ssafy.env.model.mapper.TrashMapper;

@Service
public class TrashServiceImpl implements TrashService {

  private final TrashMapper mapper;

  public TrashServiceImpl(TrashMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public List<TrashDto> getTrashInfo(String dongcode) throws SQLException {
    return mapper.getTrashInfo(dongcode);
  }
}
