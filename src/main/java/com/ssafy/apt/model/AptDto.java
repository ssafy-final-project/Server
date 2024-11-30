package com.ssafy.apt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AptDto implements Serializable{
  private static final long serialVersionUID = -1315018633501844099L;
  
  private String apt_seq;
  private String apt_name;
  private String district_code;
  private String full_code;
  private String juso;
  private String doro_juso;
  private int build_year;
  private double latitude;
  private double longitude;
  private List<AptDealDto> deals;

  private double avg_price;
  private int cnt_deals;
  private int latest_deal_year;
  private int latest_deal_month;
  private int latest_deal_day;

  // Normal field Getter/Setter
  public String getApt_seq() {
    return apt_seq;
  }

  public void setApt_seq(String apt_seq) {
    this.apt_seq = apt_seq;
  }

  public String getApt_name() {
    return apt_name;
  }

  public void setApt_name(String apt_name) {
    this.apt_name = apt_name;
  }

  public String getDistrict_code() {
    return district_code;
  }

  public void setDistrict_code(String district_code) {
    this.district_code = district_code;
  }

  public String getJuso() {
    return juso;
  }

  public void setJuso(String juso) {
    this.juso = juso;
  }

  public String getDoro_juso() {
    return doro_juso;
  }

  public void setDoro_juso(String doro_juso) {
    this.doro_juso = doro_juso;
  }

  public int getBuild_year() {
    return build_year;
  }

  public void setBuild_year(int build_year) {
    this.build_year = build_year;
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

  public List<AptDealDto> getDeals() {
    return deals;
  }

  public void addDeal(AptDealDto deal) {
    this.deals.add(deal);
  }

  public void addDeals(List<AptDealDto> deals) {
    this.deals.addAll(deals);
  }

  public String getFull_code() {
    return full_code;
  }

  public void setFull_code(String full_code) {
    this.full_code = full_code;
  }

  // Statistics field Getter/Setter
  public double getAvg_price() {
    return avg_price;
  }

  public int getCnt_deals() {
    return cnt_deals;
  }

  public int getLatest_deal_year() {
    return latest_deal_year;
  }

  public int getLatest_deal_month() {
    return latest_deal_month;
  }

  public int getLatest_deal_day() {
    return latest_deal_day;
  }

  // Constructor
  public AptDto() {
    super();
    this.deals = new ArrayList<AptDealDto>();
  }

  public AptDto(String apt_seq, String apt_name, String district_code, String juso,
      String doro_juso, int build_year, double latitude, double longitude) {
    super();
    this.apt_seq = apt_seq;
    this.apt_name = apt_name;
    this.district_code = district_code;
    this.juso = juso;
    this.doro_juso = doro_juso;
    this.build_year = build_year;
    this.latitude = latitude;
    this.longitude = longitude;

    this.deals = new ArrayList<AptDealDto>();
  }

  // Instance method
  public void refreshStatistics() {
    int sum = 0;
    for (AptDealDto deal : this.deals)
      sum += deal.getDeal_price();

    this.cnt_deals = this.deals.size();
    this.avg_price = (double) sum / this.cnt_deals;
    this.latest_deal_year = this.deals.get(this.cnt_deals - 1).getDeal_year();
    this.latest_deal_month = this.deals.get(this.cnt_deals - 1).getDeal_month();
    this.latest_deal_day = this.deals.get(this.cnt_deals - 1).getDeal_day();
  }

  public String toStringDefault() {
    return "APTDto [apt_seq=" + apt_seq + ", apt_name=" + apt_name + ", district_code="
        + district_code + ", juso=" + juso + ", doro_juso=" + doro_juso + ", build_year="
        + build_year + ", latitude=" + latitude + ", longitude=" + longitude + ", deals_size="
        + deals.size() + "]";
  }

  public String toStringStatistics() {
    return "APTDTO [avg_price=" + avg_price + ", cnt_deals=" + cnt_deals + ", latest_deal_year="
        + latest_deal_year + ", latest_deal_month=" + latest_deal_month + ", latest_deal_day="
        + latest_deal_day + "]";
  }

  @Override
  public String toString() {
    return "APTDTO [apt_seq=" + apt_seq + ", apt_name=" + apt_name + ", district_code="
        + district_code + ", full_code=" + full_code + ", juso=" + juso + ", doro_juso=" + doro_juso
        + ", build_year=" + build_year + ", latitude=" + latitude + ", longitude=" + longitude
        + ", deals=" + deals + ", avg_price=" + avg_price + ", cnt_deals=" + cnt_deals
        + ", latest_deal_year=" + latest_deal_year + ", latest_deal_month=" + latest_deal_month
        + ", latest_deal_day=" + latest_deal_day + "]";
  }
}
