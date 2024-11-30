package com.ssafy.apt.model;

import java.io.Serializable;

public class GugunStatDto implements Serializable {
  private static final long serialVersionUID = 4998038111118526948L;
  
  private String dongCode;
  private double latitude;
  private double longitude;
  private double avgPrice;
  private String statName;
  
  public double getAvgPrice() {
    return avgPrice;
  }
  public void setAvgPrice(double avgPrice) {
    this.avgPrice = avgPrice;
  }
  public String getStatName() {
    return statName;
  }
  public void setStatName(String statName) {
    this.statName = statName;
  }
  public String getDongCode() {
    return dongCode;
  }
  public void setDongCode(String dongCode) {
    this.dongCode = dongCode;
  }
  public double getLatitude() {
    return latitude;
  }
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }
  public double getLongitude() {
    return longitude;
  }
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }
  public GugunStatDto(String dongCode, double latitude, double longitude, double avgPrice,
      String statName) {
    super();
    this.dongCode = dongCode;
    this.latitude = latitude;
    this.longitude = longitude;
    this.avgPrice = avgPrice;
    this.statName = statName;
  }
  public GugunStatDto() {
    super();
  }
  @Override
  public String toString() {
    return "GugunStatDto [dongCode=" + dongCode + ", latitude=" + latitude + ", longitude="
        + longitude + ", avgPrice=" + avgPrice + ", statName=" + statName + "]";
  }
 
}
