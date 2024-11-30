package com.ssafy.notice.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.notice.model.NoticeDto;
import com.ssafy.notice.model.service.NoticeService;
import com.ssafy.util.PageNavigator;

@RestController
@RequestMapping("/notice")
public class NoticeController {
  private NoticeService noticeService;
  public NoticeController(NoticeService noticeService) {
    this.noticeService = noticeService;
  }

  @GetMapping
  public ResponseEntity<Map<String, Object>> listNotice(
      @RequestParam(value = "pageno", required = true) int pageno) throws SQLException {
    Map<String, Object> res = new HashMap<>();
    
    List<NoticeDto> list = noticeService.getNoticePage(pageno);
    PageNavigator nav = noticeService.getPageNavigator(pageno);
    
    res.put("list", list);
    res.put("nav", nav.toString());
    
    return ResponseEntity.ok(res);
  }

  @GetMapping("/{noticeno}")
  public ResponseEntity<NoticeDto> detailNotice(@PathVariable int noticeno) throws SQLException {
    return ResponseEntity.ok(noticeService.getNotice(noticeno));
  }
  
  @GetMapping("/count")
  public ResponseEntity<Integer> getNoticeCount() throws SQLException {
	    return ResponseEntity.ok(noticeService.getNoticeCount());
  }

  @PostMapping
  public ResponseEntity<Integer> registNotice(@RequestBody NoticeDto dto) throws SQLException {
    return ResponseEntity.ok(noticeService.registNotice(dto));
  }

  @DeleteMapping("/{noticeno}")
  public ResponseEntity<Integer> removeNotice(@PathVariable int noticeno) throws SQLException {
    return ResponseEntity.ok(noticeService.deleteNotice(noticeno));
  }

  @PutMapping
  public ResponseEntity<Integer> updateNotice(@RequestBody NoticeDto dto) throws SQLException {
    return ResponseEntity.ok(noticeService.updateNotice(dto));
  }
}
