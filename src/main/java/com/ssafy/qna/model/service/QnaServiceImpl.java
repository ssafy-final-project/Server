package com.ssafy.qna.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ssafy.notice.model.NoticeDto;
import com.ssafy.notice.model.mapper.NoticeMapper;
import com.ssafy.qna.model.AnswerDto;
import com.ssafy.qna.model.QuestionDto;
import com.ssafy.qna.model.mapper.QnaMapper;
import com.ssafy.util.PageConstants;
import com.ssafy.util.PageNavigator;

@Service
public class QnaServiceImpl implements QnaService {

  private final QnaMapper mapper;

  public QnaServiceImpl(QnaMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public int registQuestion(QuestionDto dto) throws Exception {
    return mapper.registQuestion(dto);
  }

  @Override
  @Transactional
  public int registAnswer(AnswerDto dto) throws Exception {
    try {
      mapper.registAnswer(dto);
      mapper.toggleQuestionResolved(dto.getQuestionId());
      return 1;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  @Override
  public int updateQuestion(QuestionDto dto) throws Exception {
    return mapper.updateQuestion(dto);
  }

  @Override
  public int updateAnswer(AnswerDto dto) throws Exception {
    return mapper.updateAnswer(dto);
  }

  @Override
  public int deleteQuestion(int question_id) throws Exception {
    return mapper.deleteQuestion(question_id);
  }

  @Override
  @Transactional
  public int deleteAnswer(Map<String, Integer> param) throws Exception {
    try {
      mapper.deleteAnswer(param);
      mapper.toggleQuestionResolved(param.get("question_id"));
      return 1;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  @Override
  public QuestionDto selectQuestion(int question_id) throws Exception {
    return mapper.selectQuestion(question_id);
  }

  @Override
  public List<QuestionDto> getQuestionPage(Map<String, Integer> param) throws Exception {
    System.out.println(mapper.getQuestionPage(param));
    return mapper.getQuestionPage(param);
  }

  @Override
  public int getQuestionCount() throws Exception {
    return mapper.getQuestionCount();
  }

  @Override
  public List<QuestionDto> getQuestionPageById(String userId) throws Exception {
    return mapper.getQuestionPageById(userId);
  }

  @Override
  public AnswerDto selectAnswer(int question_id) throws Exception {
    return mapper.selectAnswer(question_id);
  }
}
