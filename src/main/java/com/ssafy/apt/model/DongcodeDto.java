package com.ssafy.apt.model;

import java.io.Serializable;

public class DongcodeDto implements Serializable{
  private static final long serialVersionUID = 2711462160650449666L;
  
  private String dong_code;
  private String sido_name;
  private String gugun_name;
  private String dong_name;

  public String getDong_code() {
    return dong_code;
  }

  public void setDong_code(String dong_code) {
    this.dong_code = dong_code;
  }

  public String getSido_name() {
    return sido_name;
  }

  public void setSido_name(String sido_name) {
    this.sido_name = sido_name;
  }

  public String getGugun_name() {
    return gugun_name;
  }

  public void setGugun_name(String gugun_name) {
    this.gugun_name = gugun_name;
  }

  public String getDong_name() {
    return dong_name;
  }

  public void setDong_name(String dong_name) {
    this.dong_name = dong_name;
  }

  public DongcodeDto() {
    super();
  }

  public DongcodeDto(String dong_code, String sido_name, String gugun_name, String dong_name) {
    super();
    this.dong_code = dong_code;
    this.sido_name = sido_name;
    this.gugun_name = gugun_name;
    this.dong_name = dong_name;
  }

  @Override
  public String toString() {
    return "DongcodesDTO [dong_code=" + dong_code + ", sido_name=" + sido_name + ", gugun_name="
        + gugun_name + ", dong_name=" + dong_name + "]";
  }
}
