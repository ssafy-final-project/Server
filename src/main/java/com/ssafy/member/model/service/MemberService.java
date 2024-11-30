package com.ssafy.member.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.apt.model.DongcodeDto;
import com.ssafy.member.model.MemberDto;

public interface MemberService {
	/*
	 * key -> 1. id / 2. districtCode
	 */
	int addFavorite(String userId, String district) throws SQLException;

	int removeFavorite(String userId, String district) throws SQLException;

	int registerUser(MemberDto member) throws SQLException;

	int updateUser(MemberDto member) throws SQLException;

	int deleteUser(String userId) throws SQLException;

	MemberDto selectUser(String userId) throws SQLException;
	
	MemberDto login(String userId, String userPw) throws SQLException;

	List<MemberDto> selectUserAll() throws SQLException;

	/*
	 * key -> 1. id / 2. phone
	 */
	String findPw(String userId, String phone) throws SQLException;

	List<DongcodeDto> getFavorites(String userId) throws SQLException;
}
