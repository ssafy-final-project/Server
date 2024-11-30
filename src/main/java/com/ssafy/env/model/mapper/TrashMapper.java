package com.ssafy.env.model.mapper;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.env.model.TrashDto;

@Mapper
public interface TrashMapper {
  List<TrashDto> getTrashInfo(String dongcode) throws SQLException;
}
