package com.ssafy.member.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.apt.model.DongcodeDto;

public class MemberDto implements Serializable{
  private static final long serialVersionUID = -6726896564559636193L;
  
  private String id;
	private String pw;
	private String name;
	private String address;
	private String phone;
	private String salt;
	List<DongcodeDto> favorites;

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<DongcodeDto> getFavorites() {
		return favorites;
	}

	public void addFavorite(DongcodeDto favorite) {
		this.favorites.add(favorite);
	}

	public MemberDto() {
		super();
	}

	public MemberDto(String id, String pw, String name, String address, String phone) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.address = address;
		this.phone = phone;

		this.favorites = new ArrayList<DongcodeDto>();
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", pw=" + pw + ", name=" + name + ", address=" + address + ", phone=" + phone
				+ ", salt=" + salt + ", favorites=" + favorites + "]";
	}
}
