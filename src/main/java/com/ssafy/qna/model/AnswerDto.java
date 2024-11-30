package com.ssafy.qna.model;

import java.io.Serializable;
import java.util.Objects;

public class AnswerDto implements Serializable {
  private static final long serialVersionUID = -3368557738697769949L;

  private int questionId;
  private int answerId;
  private String author;
  private String content;
  private String createdAt;
  
  public int getQuestionId() {
    return questionId;
  }
  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }
  public int getAnswerId() {
    return answerId;
  }
  public void setAnswerId(int answerId) {
    this.answerId = answerId;
  }
  public String getAuthor() {
    return author;
  }
  public void setAuthor(String author) {
    this.author = author;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }
  
  
  public AnswerDto(int questionId, int answerId, String author, String content, String createdAt) {
    super();
    this.questionId = questionId;
    this.answerId = answerId;
    this.author = author;
    this.content = content;
    this.createdAt = createdAt;
  }
  public AnswerDto() {
    super();
  }
  
  @Override
  public String toString() {
    return "AnswerDto [questionId=" + questionId + ", answerId=" + answerId + ", author=" + author
        + ", content=" + content + ", createdAt=" + createdAt + "]";
  }
}
