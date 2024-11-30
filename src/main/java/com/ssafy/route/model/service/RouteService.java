package com.ssafy.route.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.apt.model.DongcodeDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.route.model.RouteDto;

public interface RouteService {
  public List<RouteDto> findRouteDijkstra(String dongcode) throws Exception;
}
