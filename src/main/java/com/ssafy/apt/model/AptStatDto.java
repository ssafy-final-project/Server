package com.ssafy.apt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AptStatDto implements Serializable{
  private static final long serialVersionUID = 7620271981312387130L;
  
  private String aptSeq;
  private int minPrice;
  private int maxPrice;
  private double avgPrice;
  private int dealCount;
  private int recentYear;
  private int recentMonth;
  private int recentDay;
  private double latitude;
  private double longitude;
  
  public String getAptSeq() {
    return aptSeq;
  }
  public void setAptSeq(String aptSeq) {
    this.aptSeq = aptSeq;
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
  public int getRecentYear() {
    return recentYear;
  }
  public void setRecentYear(int recentYear) {
    this.recentYear = recentYear;
  }
  public int getRecentMonth() {
    return recentMonth;
  }
  public void setRecentMonth(int recentMonth) {
    this.recentMonth = recentMonth;
  }
  public int getRecentDay() {
    return recentDay;
  }
  public void setRecentDay(int recentDay) {
    this.recentDay = recentDay;
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
  public AptStatDto(String aptSeq, int minPrice, int maxPrice, double avgPrice, int dealCount,
      int recentYear, int recentMonth, int recentDay, double latitude, double longitude) {
    super();
    this.aptSeq = aptSeq;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.avgPrice = avgPrice;
    this.dealCount = dealCount;
    this.recentYear = recentYear;
    this.recentMonth = recentMonth;
    this.recentDay = recentDay;
    this.latitude = latitude;
    this.longitude = longitude;
  }
  public AptStatDto() {
    super();
  }
  @Override
  public String toString() {
    return "AptStatDto [aptSeq=" + aptSeq + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice
        + ", avgPrice=" + avgPrice + ", dealCount=" + dealCount + ", recentYear=" + recentYear
        + ", recentMonth=" + recentMonth + ", recentDay=" + recentDay + ", latitude=" + latitude
        + ", longitude=" + longitude + "]";
  }
  
  
}
