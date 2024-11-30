package com.ssafy.qna.controller;

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
import com.ssafy.qna.model.AnswerDto;
import com.ssafy.qna.model.QuestionDto;
import com.ssafy.qna.model.service.QnaService;
import com.ssafy.util.PageNavigator;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/qna")
public class QnaController {
  private QnaService qnaService;
  public QnaController(QnaService qnaService) {
    this.qnaService = qnaService;
  }

  @Operation(summary = "질문 하나 자세히 보기 (댓글까지 가져옴)")
  @GetMapping("/{question_id}")
  public ResponseEntity<QuestionDto> detailQuestion(@PathVariable int question_id) throws Exception {
    return ResponseEntity.ok(qnaService.selectQuestion(question_id));
  }

  @Operation(summary = "질문 페이지 가져오기 (front에서 pagination 계산 필요)")
  @GetMapping
  public ResponseEntity<List<QuestionDto>> listQuestionPage(
      @RequestParam int from, @RequestParam int amount) throws Exception {
    Map<String, Integer> param = new HashMap<>();
    param.put("from", from);
    param.put("amount", amount);
    return ResponseEntity.ok(qnaService.getQuestionPage(param));
  }
  // 유저 아이디로 해당 유저 질문글만 가져오는 메소드 임시로 만듬
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<QuestionDto>> listQuestionPageById(@PathVariable String userId) throws Exception {
	  return ResponseEntity.ok(qnaService.getQuestionPageById(userId));
  }
  
  @Operation(summary = "총 질문 개수 가져오기 (pagination 용도)")
  @GetMapping("/count")
  public ResponseEntity<Integer> countNotice() throws Exception {
    return ResponseEntity.ok(qnaService.getQuestionCount());
  }

  @Operation(summary = "질문 등록")
  @PostMapping
  public ResponseEntity<Integer> registQuestion(@RequestBody QuestionDto dto) throws Exception {
	System.out.println(dto);
    return ResponseEntity.ok(qnaService.registQuestion(dto));
  }
  
  @Operation(summary = "특정 질문에 대한 답변 가져오기")
  @GetMapping("/answer/{question_id}")
  public ResponseEntity<AnswerDto> detailAnswer(@PathVariable int question_id) throws Exception {
    return ResponseEntity.ok(qnaService.selectAnswer(question_id));
  }

  @Operation(summary = "답변 등록")
  @PostMapping("/{question_id}")
  public ResponseEntity<Integer> registAnswer(@PathVariable int question_id, @RequestBody AnswerDto dto) throws Exception {
    dto.setQuestionId(question_id);
    return ResponseEntity.ok(qnaService.registAnswer(dto));
  }

  // UPDATE
  @Operation(summary = "질문 수정(제목, 내용만)")
  @PutMapping("/{question_id}")
  public ResponseEntity<Integer> updateQuestion(@RequestBody QuestionDto dto) throws Exception {
    return ResponseEntity.ok(qnaService.updateQuestion(dto));
  }

  @Operation(summary = "답변 수정(내용만)")
  @PutMapping("/{question_id}/{answer_id}")
  public ResponseEntity<Integer> updateAnswer(@PathVariable int question_id, @PathVariable int answer_id, @RequestBody AnswerDto dto) throws Exception {
    dto.setQuestionId(question_id);
    dto.setAnswerId(answer_id);
    return ResponseEntity.ok(qnaService.updateAnswer(dto));
  }
  
  @Operation(summary = "질문 삭제(답변 cascade)")
  @DeleteMapping("/{question_id}")
  public ResponseEntity<Integer> removeQuestion(@PathVariable int question_id) throws Exception {
    return ResponseEntity.ok(qnaService.deleteQuestion(question_id));
  }

  @Operation(summary = "답변 삭제")
  @DeleteMapping("/{question_id}/{answer_id}")
  public ResponseEntity<Integer> removeAnswer(@PathVariable int question_id, @PathVariable int answer_id) throws Exception {
    Map<String, Integer> param = new HashMap<>();
    param.put("question_id", question_id);
    param.put("answer_id", answer_id);
    return ResponseEntity.ok(qnaService.deleteAnswer(param));
  }
}
