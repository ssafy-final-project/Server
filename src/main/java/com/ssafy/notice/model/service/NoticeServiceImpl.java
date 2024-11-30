package com.ssafy.notice.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.ssafy.notice.model.NoticeDto;
import com.ssafy.notice.model.mapper.NoticeMapper;
import com.ssafy.util.PageConstants;
import com.ssafy.util.PageNavigator;

@Service
public class NoticeServiceImpl implements NoticeService {

  private final NoticeMapper mapper;

  public NoticeServiceImpl(NoticeMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public int registNotice(NoticeDto dto) throws SQLException {
    return mapper.registNotice(dto);
  }

  @Override
  public int updateNotice(NoticeDto dto) throws SQLException {
    return mapper.updateNotice(dto);
  }

  @Override
  public int deleteNotice(int notice_no) throws SQLException {
    return mapper.deleteNotice(notice_no);
  }

  @Override
  public NoticeDto getNotice(int notice_no) throws SQLException {
    mapper.updateHit(notice_no);
    NoticeDto notice = mapper.getNotice(notice_no);
    return notice;
  }

  @Override
  public List<NoticeDto> getNoticePage(int pageno) throws SQLException {
    // args change : int -> map 
    Map<String, Integer> pageMap = new HashMap<>();
    pageMap.put("from", PageConstants.listSize * pageno - PageConstants.listSize);
    pageMap.put("amount", PageConstants.listSize);
    return mapper.getNoticePage(pageMap);
  }
  

  @Override
  public PageNavigator getPageNavigator(int page_no) throws SQLException {
      PageNavigator pn = new PageNavigator();
      
      int listSize = PageConstants.listSize;
      int navSize = PageConstants.navSize;
      int noticeCount = getNoticeCount();
      int totalPageCount = (noticeCount-1) / listSize + 1;
      
      pn.setStartRange(page_no < navSize);
      pn.setEndRange((totalPageCount-1)/navSize*navSize < page_no);
      pn.setTotalCount(noticeCount);
      pn.setTotalPageCount(totalPageCount);
      pn.setCurrentPage(page_no);
      pn.setNavSize(navSize);
      
      return pn;
  }
  
  @Override
  public int getNoticeCount() throws SQLException {
    return mapper.getNoticeCount();
  }
}
