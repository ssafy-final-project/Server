package com.ssafy.env.model;

import java.io.Serializable;

public class NearInfoDto implements Serializable {
  private static final long serialVersionUID = 1897995492716546138L;
  
  private String largeCity; // 광역시도
  private String district; // 시군구
  private String majorCategory; // 업종대분류
  private String middleCategory; // 업종중분류
  private String minorCategory; // 업종소분류
  private int count; // 건수

  // Getters and Setters
  public String getLargeCity() {
    return largeCity;
  }

  public void setLargeCity(String largeCity) {
    this.largeCity = largeCity;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getMajorCategory() {
    return majorCategory;
  }

  public void setMajorCategory(String majorCategory) {
    this.majorCategory = majorCategory;
  }

  public String getMiddleCategory() {
    return middleCategory;
  }

  public void setMiddleCategory(String middleCategory) {
    this.middleCategory = middleCategory;
  }

  public String getMinorCategory() {
    return minorCategory;
  }

  public void setMinorCategory(String minorCategory) {
    this.minorCategory = minorCategory;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  @Override
  public String toString() {
    return "InfoDTO{" + "largeCity='" + largeCity + '\'' + ", district='" + district + '\''
        + ", majorCategory='" + majorCategory + '\'' + ", middleCategory='" + middleCategory + '\''
        + ", minorCategory='" + minorCategory + '\'' + ", count=" + count + '}';
  }

}
