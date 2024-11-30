package com.ssafy.notice.model;

import java.io.Serializable;
import java.util.Objects;

public class NoticeDto implements Serializable{
  private static final long serialVersionUID = -3368557738697769949L;
  
  private int notice_no;
	private String title;
	private String author;
	private String content;
	private int hit;
	private String notice_date;

	
	public NoticeDto(String title, String author, String content) {
		this.title = title;
		this.author = author;
		this.content = content;
	}

	public int getNotice_no() {
		return notice_no;
	}

	public void setNotice_no(int notice_no) {
		this.notice_no = notice_no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getNotice_date() {
		return notice_date;
	}

	public void setNotice_date(String notice_date) {
		this.notice_date = notice_date;
	}

	public NoticeDto(int notice_no, String title, String author, String content, int hit, String notice_date) {
		super();
		this.notice_no = notice_no;
		this.title = title;
		this.author = author;
		this.content = content;
		this.hit = hit;
		this.notice_date = notice_date;
	}

	public NoticeDto() {
		super();
	}

	@Override
	public String toString() {
		return "NoticeDTO [notice_no=" + notice_no + ", title=" + title + ", author=" + author + ", content=" + content
				+ ", hit=" + hit + ", notice_date=" + notice_date + "]";
	}
}
