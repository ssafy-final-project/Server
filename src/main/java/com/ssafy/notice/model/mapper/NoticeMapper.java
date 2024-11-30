package com.ssafy.notice.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ssafy.notice.model.NoticeDto;


@Mapper
public interface NoticeMapper {
  int registNotice(NoticeDto dto) throws SQLException;

  int updateNotice(NoticeDto dto) throws SQLException;

  int deleteNotice(int notice_no) throws SQLException;

  NoticeDto getNotice(int notice_no) throws SQLException;

  /*
   * 인자 Map<String, Integer>로 변경 key - 1. from / 2. amount
   */
  List<NoticeDto> getNoticePage(Map<String, Integer> pageMap) throws SQLException;

  int getNoticeCount() throws SQLException;
  
  void updateHit(int notice_no) throws SQLException;
}
