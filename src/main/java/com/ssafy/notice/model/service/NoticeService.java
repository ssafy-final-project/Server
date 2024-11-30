package com.ssafy.notice.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.notice.model.NoticeDto;
import com.ssafy.util.PageNavigator;


public interface NoticeService {
  int registNotice(NoticeDto dto) throws SQLException;

  int updateNotice(NoticeDto dto) throws SQLException;

  int deleteNotice(int notice_no) throws SQLException;

  NoticeDto getNotice(int notice_no) throws SQLException;

  List<NoticeDto> getNoticePage(int pageno) throws SQLException;

  PageNavigator getPageNavigator(int page_no) throws SQLException;

  int getNoticeCount() throws SQLException;
  
}
