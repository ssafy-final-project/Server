package com.ssafy.apt.model;

import java.io.Serializable;

public class DongStatDto implements Serializable {
  private static final long serialVersionUID = -252468785991946672L;
  
  private String dongCode;
  private int minPrice;
  private int maxPrice;
  private double avgPrice;
  private int dealCount;
  private double latitude;
  private double longitude;
  private String superName;
  private String statName;

  public String getSuperName() {
    return superName;
  }
  public void setSuperName(String superName) {
    this.superName = superName;
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
  public int getMinPrice() {
    return minPrice;
  }
  public void setMinPrice(int minPrice) {
    this.minPrice = minPrice;
  }
  public int getMaxPrice() {
    return maxPrice;
  }
  public void setMaxPrice(int maxPrice) {
    this.maxPrice = maxPrice;
  }
  public double getAvgPrice() {
    return avgPrice;
  }
  public void setAvgPrice(double avgPrice) {
    this.avgPrice = avgPrice;
  }
  public int getDealCount() {
    return dealCount;
  }
  public void setDealCount(int dealCount) {
    this.dealCount = dealCount;
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
  
  
  
  public DongStatDto(String dongCode, int minPrice, int maxPrice, double avgPrice, int dealCount,
      double latitude, double longitude, String superName, String statName) {
    super();
    this.dongCode = dongCode;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.avgPrice = avgPrice;
    this.dealCount = dealCount;
    this.latitude = latitude;
    this.longitude = longitude;
    this.superName = superName;
    this.statName = statName;
  }
  public DongStatDto() {
    super();
  }
  @Override
  public String toString() {
    return "DongStatDto [dongCode=" + dongCode + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice
        + ", avgPrice=" + avgPrice + ", dealCount=" + dealCount + ", latitude=" + latitude
        + ", longitude=" + longitude + ", superName=" + superName + ", statName=" + statName + "]";
  }
  
  
}
  
