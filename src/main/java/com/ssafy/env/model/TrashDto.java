package com.ssafy.env.model;

import java.io.Serializable;

public class TrashDto implements Serializable{
  private static final long serialVersionUID = -8946189169693015647L;
  
  private int id;
  private String city;
  private String district;
  private String bagType;
  private String disposalMethod;
  private String usage;
  private String target;
  private String managingDepartment;
  private String departmentPhone;
  private String type;
  private int price;

  public int getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getBagType() {
    return bagType;
  }

  public void setBagType(String bagType) {
    this.bagType = bagType;
  }

  public String getDisposalMethod() {
    return disposalMethod;
  }

  public void setDisposalMethod(String disposalMethod) {
    this.disposalMethod = disposalMethod;
  }

  public String getUsage() {
    return usage;
  }

  public void setUsage(String usage) {
    this.usage = usage;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getManagingDepartment() {
    return managingDepartment;
  }

  public void setManagingDepartment(String managingDepartment) {
    this.managingDepartment = managingDepartment;
  }

  public String getDepartmentPhone() {
    return departmentPhone;
  }

  public void setDepartmentPhone(String departmentPhone) {
    this.departmentPhone = departmentPhone;
  }
}
