package com.ssafy.route.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apt.model.DongcodeDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.route.model.RouteDto;

@Mapper
public interface RouteMapper {
	List<RouteDto> getRouteAll() throws Exception;
}
