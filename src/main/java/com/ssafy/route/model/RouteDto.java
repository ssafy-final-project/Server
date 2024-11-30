package com.ssafy.route.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.apt.model.DongcodeDto;

public class RouteDto implements Serializable {

  private static final long serialVersionUID = 4879430954043738767L;
  
  private String nodeFrom;
  private String nodeTo;
  private Double durationWeight;

  public String getNodeFrom() {
    return nodeFrom;
  }

  public void setNodeFrom(String nodeForm) {
    this.nodeFrom = nodeForm;
  }

  public String getNodeTo() {
    return nodeTo;
  }

  public void setNodeTo(String nodeTo) {
    this.nodeTo = nodeTo;
  }

  public Double getDurationWeight() {
    return durationWeight;
  }

  public void setDurationWeight(double durationWeight) {
    this.durationWeight = durationWeight;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public RouteDto(String nodeFrom, String nodeTo, Double durationWeight) {
    super();
    this.nodeFrom = nodeFrom;
    this.nodeTo = nodeTo;
    this.durationWeight = durationWeight;
  }

  public RouteDto() {
    super();
  }

  @Override
  public String toString() {
    return "RouteDto [nodeFrom=" + nodeFrom + ", nodeTo=" + nodeTo + ", durationWeight="
        + durationWeight + "]";
  }
}
