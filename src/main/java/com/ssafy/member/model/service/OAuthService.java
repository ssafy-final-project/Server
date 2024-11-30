package com.ssafy.member.model.service;

import com.ssafy.member.model.MemberDto;

public interface OAuthService {
  public String getKakaoAccessToken(String restKey, String redirect, String code) throws Exception;
  public MemberDto getKakaoUserInfo(String accessToken) throws Exception;
}
