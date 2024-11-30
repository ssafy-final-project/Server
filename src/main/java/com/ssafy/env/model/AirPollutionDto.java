package com.ssafy.env.model;

import java.io.Serializable;

public class AirPollutionDto implements Serializable{
  private static final long serialVersionUID = -8538622872789601979L;
  
  private int locCode;
  private String locOfficial;
  private String locAddress;
  private String locName;
  private int displayNo;
  private String dongcode;
  private double avgpm2_5;
  private double avgpm10;

  public double getAvgpm2_5() {
    return avgpm2_5;
  }

  public void setAvgpm2_5(double avgpm2_5) {
    this.avgpm2_5 = avgpm2_5;
  }

  public double getAvgpm10() {
    return avgpm10;
  }

  public void setAvgpm10(double avgpm10) {
    this.avgpm10 = avgpm10;
  }

  public int getLocCode() {
    return locCode;
  }

  public void setLocCode(int locCode) {
    this.locCode = locCode;
  }

  public String getLocOfficial() {
    return locOfficial;
  }

  public void setLocOfficial(String locOfficial) {
    this.locOfficial = locOfficial;
  }

  public String getLocAddress() {
    return locAddress;
  }

  public void setLocAddress(String locAddress) {
    this.locAddress = locAddress;
  }

  public String getLocName() {
    return locName;
  }

  public void setLocName(String locName) {
    this.locName = locName;
  }

  public int getDisplayNo() {
    return displayNo;
  }

  public void setDisplayNo(int displayNo) {
    this.displayNo = displayNo;
  }

  public String getDongcode() {
    return dongcode;
  }

  public void setDongcode(String dongcode) {
    this.dongcode = dongcode;
  }


}
