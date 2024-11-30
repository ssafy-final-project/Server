package com.ssafy.member.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apt.model.DongcodeDto;
import com.ssafy.member.model.MemberDto;

@Mapper
public interface MemberMapper {
	/*
	 * key -> 1. id / 2. districtCode
	 */
	int addFavorite(Map<String, String> userMap) throws SQLException;

	int removeFavorite(Map<String, String> userMap) throws SQLException;

	int registerUser(MemberDto member) throws SQLException;

	int updateUser(MemberDto member) throws SQLException;

	int deleteUser(String userId) throws SQLException;

	MemberDto selectUser(String userId) throws SQLException;

	List<MemberDto> selectUserAll() throws SQLException;

	/*
	 * key -> 1. id / 2. phone
	 */
	String findPw(Map<String, String> paramMap) throws SQLException;

	List<DongcodeDto> getFavorites(String userId) throws SQLException;
}
