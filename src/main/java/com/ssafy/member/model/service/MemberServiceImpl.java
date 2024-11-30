package com.ssafy.member.model.service;

import java.sql.SQLException;
import java.util.HashMap;
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
import com.ssafy.apt.model.AptDto;
import com.ssafy.apt.model.DongcodeDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.mapper.MemberMapper;
import com.ssafy.util.Encryptor;

@Service
public class MemberServiceImpl implements MemberService {
  private final MemberMapper mapper;

  public MemberServiceImpl(MemberMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public int addFavorite(String userId, String district) throws SQLException {
    Map<String, String> favMap = new HashMap<>();
    favMap.put("userId", userId);
    favMap.put("districtCode", district.concat("00000"));
    return mapper.addFavorite(favMap);
  }

  @Override
  public int removeFavorite(String userId, String district) throws SQLException {
    Map<String, String> favMap = new HashMap<>();
    favMap.put("userId", userId);
    favMap.put("districtCode", district.concat("00000"));
    return mapper.removeFavorite(favMap);
  }

  @Override
  public int registerUser(MemberDto member) throws SQLException {
    encodePw(member, Encryptor.getSalt());
    return mapper.registerUser(member);
  }

  @Override
  public int updateUser(MemberDto member) throws SQLException {
    encodePw(member, selectUser(member.getId()).getSalt());
    return mapper.updateUser(member);
  }

  @Override
  public int deleteUser(String userId) throws SQLException {
    return mapper.deleteUser(userId);
  }

  @Override
  public MemberDto selectUser(String userId) throws SQLException {
    return mapper.selectUser(userId);
  }

  @Override
  public List<MemberDto> selectUserAll() throws SQLException {
    return mapper.selectUserAll();
  }

  @Override
  public String findPw(String userId, String phone) throws SQLException {
    Map<String, String> userMap = new HashMap<>();
    userMap.put("userId", userId);
    userMap.put("phone", phone);
    return mapper.findPw(userMap);
  }

  @Override
  public List<DongcodeDto> getFavorites(String userId) throws SQLException {
    return mapper.getFavorites(userId);
  }

  @Override
  public MemberDto login(String userId, String userPw) throws SQLException {
    MemberDto member = mapper.selectUser(userId);
    if (member == null) return null;
    String salt = member.getSalt();
    String encodedPw = Encryptor.getEncryptedText(userPw, salt);
    if (!encodedPw.equals(member.getPw())) return null;
    return member;
  }

  private void encodePw(MemberDto member, String salt) {
    String pw = member.getPw();
    String encodedPw = Encryptor.getEncryptedText(pw, salt);
    member.setPw(encodedPw);
    member.setSalt(salt);
  }
}
