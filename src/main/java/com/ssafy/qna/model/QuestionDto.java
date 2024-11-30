package com.ssafy.qna.model;

import java.io.Serializable;
import java.util.List;

public class QuestionDto implements Serializable {
  private static final long serialVersionUID = -3368557738697769949L;

  private int questionId;
  private String category;
  private String author;
  private String title;
  private String content;
  private String createdAt;
  private int isResolved;
  private List<AnswerDto> answers;

  public int getIsResolved() {
    return isResolved;
  }

  public void setIsResolved(int isResolved) {
    this.isResolved = isResolved;
  }

  public List<AnswerDto> getAnswers() {
    return answers;
  }

  public void setAnswers(List<AnswerDto> answers) {
    this.answers = answers;
  }

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public QuestionDto(int questionId, String category, String author, String title, String content,
      String createdAt, int isResolved, List<AnswerDto> answers) {
    super();
    this.questionId = questionId;
    this.category = category;
    this.author = author;
    this.title = title;
    this.content = content;
    this.createdAt = createdAt;
    this.isResolved = isResolved;
    this.answers = answers;
  }

  public QuestionDto() {
    super();
  }

  @Override
  public String toString() {
    return "QuestionDto [questionId=" + questionId + ", category=" + category + ", author=" + author
        + ", title=" + title + ", content=" + content + ", createdAt=" + createdAt + ", isResolved="
        + isResolved + ", answers=" + answers + "]";
  }
}
