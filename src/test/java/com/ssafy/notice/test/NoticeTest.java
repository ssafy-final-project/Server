package com.ssafy.notice.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import com.ssafy.notice.model.NoticeDto;
import com.ssafy.notice.model.mapper.NoticeMapper;
import com.ssafy.util.PageConstants;

@MybatisTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NoticeTest {
	
	@Autowired
	private NoticeMapper noticeMapper;
	private NoticeDto notice;
	
	{
		notice = new NoticeDto();
		notice.setTitle("title");
		notice.setAuthor("ssafy");
		notice.setContent("content");
	}
	
	@Test
	void registTest() throws SQLException {
		int result = noticeMapper.registNotice(notice);
		assertThat(result).isEqualTo(1);
	}
	
	@Test
	void deleteTest() throws SQLException {
		int result = noticeMapper.deleteNotice(1);
		assertThat(result).isEqualTo(1);
	}
	
	@Test
	void getTest() throws SQLException {
		int prevCnt = noticeMapper.getNoticeCount();
		noticeMapper.registNotice(notice);
		int nextCnt = noticeMapper.getNoticeCount();
		assertThat(prevCnt).isEqualTo(nextCnt-1);
		
		NoticeDto getNotice = noticeMapper.getNotice(31);
		assertThat(getNotice.getTitle()).isEqualTo("테스트");
		assertThat(getNotice.getContent()).isEqualTo("나는테스트");
	}
	
	@Test
	void updateTest() throws SQLException {
		NoticeDto newNotice = new NoticeDto("updateTitle", "updateAuthor", "updateContent");
		newNotice.setNotice_no(31);
		int result = noticeMapper.updateNotice(newNotice);
		assertThat(result).isEqualTo(1);
		
		NoticeDto getNotice = noticeMapper.getNotice(31);
		assertThat(getNotice.getTitle()).isEqualTo("updateTitle");
		assertThat(getNotice.getContent()).isEqualTo("updateContent");
	}
	
	@Test
	void listTest() throws SQLException {
		Map<String, Integer> pageMap = new HashMap<>();
		pageMap.put("from", PageConstants.listSize * 2 - PageConstants.listSize);
		pageMap.put("amount", PageConstants.listSize);
		List<NoticeDto> noticeList = noticeMapper.getNoticePage(pageMap);
		noticeList.stream().forEach(System.out::println);
	}
}
