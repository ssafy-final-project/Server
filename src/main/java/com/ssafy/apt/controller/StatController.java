package com.ssafy.apt.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.apt.model.AptDto;
import com.ssafy.apt.model.AptStatDto;
import com.ssafy.apt.model.DongStatDto;
import com.ssafy.apt.model.GugunStatDto;
import com.ssafy.apt.model.service.DongcodeService;
import com.ssafy.apt.model.service.StatService;
import com.ssafy.util.MSTBuilder;

@RestController
@RequestMapping("/stat")
public class StatController {

  private final StatService statService;
  private final DongcodeService dongcodeService;

  public StatController(StatService statService, DongcodeService dongcodeService) {
    this.statService = statService;
    this.dongcodeService = dongcodeService;
  }

  @GetMapping("/gugun")
  public ResponseEntity<List<GugunStatDto>> getGugunAll() throws Exception {
      return ResponseEntity.ok(statService.getGugunAll());
  }
  
  @GetMapping("/dong")
  public ResponseEntity<List<DongStatDto>> getDongStatAll() throws Exception {
      return ResponseEntity.ok(statService.getDongStatAll());
  }
  
  @GetMapping("/dong/{shortDongcode}")
  public ResponseEntity<List<DongStatDto>> getDongStatByGugun(@PathVariable String shortDongcode) throws Exception {
      return ResponseEntity.ok(statService.getDongStatByGugun(shortDongcode));
  }
  
  @GetMapping("/dong/name")
  public ResponseEntity<String> getDongStatByName(@RequestParam String sido_name, @RequestParam String gugun_name, @RequestParam String dong_name) throws Exception {
      return ResponseEntity.ok(dongcodeService.findDongcodeByName(sido_name, gugun_name, dong_name));
  }
  
  @GetMapping("/apt/{shortDongcode}")
  public ResponseEntity<List<AptStatDto>> getAptStatByDong(@PathVariable String shortDongcode) throws Exception {
      return ResponseEntity.ok(statService.getAptStatByDong(shortDongcode));
  }
  
  @GetMapping("/apt/single/{aptSeq}")
  public ResponseEntity<AptStatDto> getAptStat(@PathVariable String aptSeq) throws Exception {
      return ResponseEntity.ok(statService.getAptStat(aptSeq));
  }

  @GetMapping("/mstlike")
  public ResponseEntity<List<Object[]>> startMSTLikeGraphBuilder() throws Exception {
    return ResponseEntity.ok(MSTBuilder.main(statService.getDongStatAll()));
  }

  @GetMapping("/mstlike/index")
  public ResponseEntity<List<Object[]>> logGraphIndicies() throws Exception {
    return ResponseEntity.ok(MSTBuilder.main(statService.getDongStatAll()));
  }
  
  @GetMapping("/aptInfo")
  public ResponseEntity<List<AptDto>> getAptInfoAll() throws Exception {
    return ResponseEntity.ok(statService.getAptInfoAll());
  }
}
