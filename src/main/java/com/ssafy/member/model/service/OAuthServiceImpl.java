package com.ssafy.member.model.service;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.ssafy.member.model.MemberDto;
import com.ssafy.util.Encryptor;

@Service
public class OAuthServiceImpl implements OAuthService {
  public String getKakaoAccessToken(String restKey, String redirect, String code) throws Exception {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("client_id", restKey);
    params.add("redirect_uri", redirect);
    params.add("code", code);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    ResponseEntity<Map> response =
        restTemplate.postForEntity("https://kauth.kakao.com/oauth/token", request, Map.class);

    System.out.println("responseToken = " + response);
    return (String) response.getBody().get("access_token");
  }

  @SuppressWarnings("unchecked")
  public MemberDto getKakaoUserInfo(String accessToken) throws Exception {
    RestTemplate restTemplate = new RestTemplate();

    // 1. 헤더 설정
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + accessToken);

    // 2. 요청 전송
    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<Map> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
        HttpMethod.GET, entity, Map.class);

    Map<String, Object> kakaoAccount =
        (Map<String, Object>) response.getBody().get("kakao_account");
    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
    String nickname = (String) profile.get("nickname");
    String email = (String) kakaoAccount.get("email");
    
    MemberDto member = new MemberDto();
    member.setName(nickname);
    member.setAddress("-");
    member.setPhone("-");
    member.setPw("");
    member.setId("kakao_" + Encryptor.getEncryptedTextWithNoSalt(email));

    return member;
  }
}
