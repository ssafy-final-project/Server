package com.ssafy.apt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.apt.model.AptDealDto;
import com.ssafy.apt.model.AptDto;
import com.ssafy.apt.model.service.AptDealService;

@RestController
@RequestMapping("/apt")
public class AptController {

  private final AptDealService aptDealService;

  public AptController(AptDealService aptDealService) {
    this.aptDealService = aptDealService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<AptDto> detail(@PathVariable("id") String id) {
    return ResponseEntity.ok(aptDealService.getAptDataBySeq(id));
  }

  @GetMapping
  public ResponseEntity<List<AptDto>> list(@RequestParam Map<String, String> params) {
    System.out.println(params);
    return ResponseEntity.ok(aptDealService.getDealDataByCond(params));
  }

}
