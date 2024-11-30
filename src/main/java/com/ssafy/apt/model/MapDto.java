package com.ssafy.apt.model;

public class MapDto {
  double lat;
  double lng;

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public MapDto(double lat, double lng) {
    super();
    this.lat = lat;
    this.lng = lng;
  }

  public MapDto() {
    super();
  }

  @Override
  public String toString() {
    return "MapDTO [lat=" + lat + ", lng=" + lng + "]";
  }
}
