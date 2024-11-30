package com.ssafy.member.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.apt.model.DongcodeDto;
import com.ssafy.env.model.TrashDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;
import com.ssafy.member.model.service.OAuthService;
import com.ssafy.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {

  @Value("${kakao.client-id}")
  private String kakaoClientId;

  @Value("${kakao.redirect-uri}")
  private String kakaoRedirectUri;

  MemberService memberService;
  OAuthService oauthService;
  JWTUtil jwtUtil;

  public MemberController(MemberService memberService, OAuthService oauthService, JWTUtil jwtUtil) {
    this.memberService = memberService;
    this.oauthService = oauthService;
    this.jwtUtil = jwtUtil;
  }

  @GetMapping("/{userId}")
  public ResponseEntity<MemberDto> detailMember(@PathVariable String userId) throws SQLException {
    return ResponseEntity.ok(memberService.selectUser(userId));
  }

  @PostMapping()
  public ResponseEntity<Integer> registMember(@RequestBody MemberDto member) throws SQLException {
    return ResponseEntity.ok(memberService.registerUser(member));
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Integer> removeMember(@PathVariable String userId) throws SQLException {
    return ResponseEntity.ok(memberService.deleteUser(userId));
  }

  @PutMapping
  public ResponseEntity<Integer> updateMember(@RequestBody MemberDto member) throws SQLException {
    return ResponseEntity.ok(memberService.updateUser(member));
  }

  @GetMapping("/info/{userId}")
  public ResponseEntity<MemberDto> getUserInfo(@PathVariable String userId) throws SQLException {
    MemberDto member = memberService.selectUser(userId);
    member.setPw(null);
    member.setSalt(null);
    if (member.getId() == null)
      return ResponseEntity.internalServerError().build();
    return ResponseEntity.ok(member);
  }

  @GetMapping("/logout")
  public ResponseEntity<Integer> logoutMember(HttpSession session) {
    session.removeAttribute("userInfo");
    return ResponseEntity.ok(1);
  }

  @GetMapping("/idchk/{userId}")
  public ResponseEntity<Integer> checkMember(@PathVariable String userId) throws SQLException {
    MemberDto chkMember = memberService.selectUser(userId);
    int exist = chkMember == null ? 0 : 1;
    return ResponseEntity.ok(exist);
  }

  @GetMapping("/favorite/{userId}")
  public ResponseEntity<List<DongcodeDto>> getFavorites(@PathVariable String userId)
      throws SQLException {
    return ResponseEntity.ok(memberService.getFavorites(userId));
  }

  @PostMapping("/favorite/{userId}/{dongcode}")
  public ResponseEntity<Integer> addFavorite(@PathVariable String userId,
      @PathVariable String dongcode) throws SQLException {
    return ResponseEntity.ok(memberService.addFavorite(userId, dongcode));
  }

  @DeleteMapping("/favorite/{userId}/{dongcode}")
  public ResponseEntity<Integer> removeFavorite(@PathVariable String userId,
      @PathVariable String dongcode) throws SQLException {
    return ResponseEntity.ok(memberService.removeFavorite(userId, dongcode));
  }


  @Operation(summary = "JWT 토큰 발급 테스트")
  @PostMapping("/jwt/test")
  public ResponseEntity<String> jwtTest(@RequestBody MemberDto dto) {
    return ResponseEntity.ok(jwtUtil.createAccessToken(dto.getId()));
  }

  @Operation(summary = "JWT 토큰 유효성 검사 테스트")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "유효한 토큰입니다."),
      @ApiResponse(responseCode = "401", description = "만료되었거나 유효하지 않은 토큰입니다.")})
  @GetMapping("/jwt/test/{token}")
  public ResponseEntity<Boolean> jwtChkTest(@PathVariable String token) {
    try {
      jwtUtil.checkToken(token);
      return ResponseEntity.ok(true);
    } catch (ExpiredJwtException e) {
      return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    } catch (Exception e) {
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
  }

  @Operation(summary = "JWT 로그인")
  @PostMapping("/jwt/login")
  public ResponseEntity<Map<String, Object>> jwtLogin(@RequestBody MemberDto dto,
      HttpServletResponse response) throws SQLException {
    Map<String, Object> res = new HashMap<>();
    MemberDto mem = memberService.login(dto.getId(), dto.getPw());
    HttpStatus status = null;

    if (mem != null) {
      String accessToken = jwtUtil.createAccessToken(mem.getId());

      String accessTokenCookie =
          String.format("access-token=%s; Path=/; HttpOnly; Secure=%b; SameSite=None; Max-Age=%d",
              accessToken, true, (int) (JWTUtil.accessTokenExpireTime / 1000));
      response.addHeader("Set-Cookie", accessTokenCookie);

      String uidTokenCookie =
          String.format("uid=%s; Path=/; HttpOnly; Secure=%b; SameSite=None; Max-Age=%d",
              dto.getId(), true, (int) (JWTUtil.accessTokenExpireTime / 1000));
      response.addHeader("Set-Cookie", uidTokenCookie);

      res.put("message", "로그인 성공");
      status = HttpStatus.ACCEPTED;
    } else {
      res.put("message", "아이디 또는 패스워드를 확인해주세요.");
      status = HttpStatus.UNAUTHORIZED;
    }

    return new ResponseEntity<>(res, status);
  }

  @Operation(summary = "JWT 인증")
  @GetMapping("/jwt/auth")
  public ResponseEntity<Map<String, Object>> jwtAuthenticate(
      @CookieValue(name = "access-token") String accessToken,
      @CookieValue(name = "uid") String uidToken, HttpServletResponse response)
      throws SQLException {
    Map<String, Object> res = new HashMap<>();
    HttpStatus status = null;

    try {
      Claims claims = jwtUtil.checkToken(accessToken);

      Date exp = claims.getExpiration();
      String uid = (String) claims.get("uid");

      if (!uidToken.equals(uid))
        throw new Exception();

      long rem = exp.getTime() - System.currentTimeMillis();

      if (rem < 30 * 60000) {
        String newAccessToken = jwtUtil.createAccessToken(uid);

        String accessTokenCookie =
            String.format("access-token=%s; Path=/; HttpOnly; Secure=%b; SameSite=None; Max-Age=%d",
                newAccessToken, true,
                (int) (JWTUtil.accessTokenExpireTime / 1000));
        response.addHeader("Set-Cookie", accessTokenCookie);

        String uidTokenCookie =
            String.format("uid=%s; Path=/; HttpOnly; Secure=%b; SameSite=None; Max-Age=%d",
                uidToken, true, (int) (JWTUtil.accessTokenExpireTime / 1000));
        response.addHeader("Set-Cookie", uidTokenCookie);
      }

      res.put("message", "인증 성공");
      res.put("uid", uid);
      status = HttpStatus.OK;
    } catch (Exception e) {
      e.printStackTrace();
      res.put("message", "토큰 만료");
      status = HttpStatus.UNAUTHORIZED;
    }

    return new ResponseEntity<>(res, status);
  }

  @GetMapping("/oauth/kakao")
  public ResponseEntity<Map<String, String>> kakaoLogin() {
    String redirectUri = "https://kauth.kakao.com/oauth/authorize";
    String responseType = "code";

    String kakaoAuthUrl = redirectUri + "?client_id=" + kakaoClientId + "&redirect_uri="
        + kakaoRedirectUri + "&response_type=" + responseType;

    Map<String, String> response = new HashMap<>();
    response.put("redirectUrl", kakaoAuthUrl);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/oauth/kakao/callback")
  public ResponseEntity<Void> handleKakaoCallback(@RequestParam String code,
      HttpServletResponse response) throws Exception {
    String kakaoAccessToken =
        oauthService.getKakaoAccessToken(kakaoClientId, kakaoRedirectUri, code);
    MemberDto kakaoMember = oauthService.getKakaoUserInfo(kakaoAccessToken);
    System.out.println(kakaoMember);

    MemberDto existingMember = memberService.selectUser(kakaoMember.getId());
    if (existingMember == null) {
      memberService.registerUser(kakaoMember);
      System.out.println("New user registered: " + kakaoMember.getId());
    } else {
      System.out.println("User already exists: " + existingMember.getId());
    }

    String jwtAccessToken = jwtUtil.createAccessToken(kakaoMember.getId());

    String accessTokenCookie =
        String.format("access-token=%s; Path=/; HttpOnly; Secure=%b; SameSite=None; Max-Age=%d",
            jwtAccessToken, true, (int) (JWTUtil.accessTokenExpireTime / 1000));
    response.addHeader("Set-Cookie", accessTokenCookie);

    String uidTokenCookie =
        String.format("uid=%s; Path=/; HttpOnly; Secure=%b; SameSite=None; Max-Age=%d",
            kakaoMember.getId(), true, (int) (JWTUtil.accessTokenExpireTime / 1000));
    response.addHeader("Set-Cookie", uidTokenCookie);

    response.setHeader("Location", "http://192.168.205.56:5173/");
    response.setStatus(HttpServletResponse.SC_FOUND); // 302 Redirect
    return ResponseEntity.status(HttpServletResponse.SC_FOUND).build();
  }
}
