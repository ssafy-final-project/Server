package com.ssafy.qna.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.notice.model.NoticeDto;
import com.ssafy.qna.model.AnswerDto;
import com.ssafy.qna.model.QuestionDto;
import com.ssafy.util.PageNavigator;

public interface QnaService {
  int registQuestion(QuestionDto dto) throws Exception;
  int registAnswer(AnswerDto dto) throws Exception;
  int updateQuestion(QuestionDto dto) throws Exception;
  int updateAnswer(AnswerDto dto) throws Exception;
  int deleteQuestion(int question_id) throws Exception;
  int deleteAnswer(Map<String, Integer> param) throws Exception;
  
  QuestionDto selectQuestion(int question_id) throws Exception;
  List<QuestionDto> getQuestionPage(Map<String, Integer> param) throws Exception;
  int getQuestionCount() throws Exception;
  List<QuestionDto> getQuestionPageById(String userId) throws Exception;
  
  // Answer 
  AnswerDto selectAnswer(int question_id) throws Exception;
}
