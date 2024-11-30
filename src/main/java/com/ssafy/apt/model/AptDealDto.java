package com.ssafy.apt.model;

import java.io.Serializable;

public class AptDealDto implements Comparable<AptDealDto>, Serializable {

  private static final long serialVersionUID = -3543370613816941257L;
  
  private int no;
  private String apt_dong;
  private String floor;
  private int deal_year;
  private int deal_month;
  private int deal_day;
  private double exclu_use_ar;
  private int deal_price;

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getApt_dong() {
    return apt_dong;
  }

  public void setApt_dong(String apt_dong) {
    this.apt_dong = apt_dong;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String string) {
    this.floor = string;
  }

  public int getDeal_year() {
    return deal_year;
  }

  public void setDeal_year(int deal_year) {
    this.deal_year = deal_year;
  }

  public int getDeal_month() {
    return deal_month;
  }

  public void setDeal_month(int deal_month) {
    this.deal_month = deal_month;
  }

  public int getDeal_day() {
    return deal_day;
  }

  public void setDeal_day(int deal_day) {
    this.deal_day = deal_day;
  }

  public double getExclu_use_ar() {
    return exclu_use_ar;
  }

  public void setExclu_use_ar(double exclu_use_ar) {
    this.exclu_use_ar = exclu_use_ar;
  }

  public int getDeal_price() {
    return deal_price;
  }

  public void setDeal_price(int deal_price) {
    this.deal_price = deal_price;
  }

  public AptDealDto() {
    super();
  }

  public AptDealDto(int no, String apt_dong, String floor, int deal_year, int deal_month,
      int deal_day, double exclu_use_ar, int deal_price) {
    super();
    this.no = no;
    this.apt_dong = apt_dong;
    this.floor = floor;
    this.deal_year = deal_year;
    this.deal_month = deal_month;
    this.deal_day = deal_day;
    this.exclu_use_ar = exclu_use_ar;
    this.deal_price = deal_price;
  }

  @Override
  public String toString() {
    return "APTDealDto [no=" + no + ", apt_dong=" + apt_dong + ", floor=" + floor + ", deal_year="
        + deal_year + ", deal_month=" + deal_month + ", deal_day=" + deal_day + ", exclu_use_ar="
        + exclu_use_ar + ", deal_amount=" + deal_price + "]";
  }

  @Override
  public int compareTo(AptDealDto o) {
    if (this.deal_year != o.deal_year)
      return this.deal_year - o.deal_year;
    if (this.deal_month != o.deal_month)
      return this.deal_month - o.deal_month;
    return this.deal_day - o.deal_day;
  }

}
