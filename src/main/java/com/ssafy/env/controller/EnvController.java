package com.ssafy.env.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.apt.model.AptDealDto;
import com.ssafy.apt.model.AptDto;
import com.ssafy.env.model.AirPollutionDto;
import com.ssafy.env.model.TrashDto;
import com.ssafy.env.model.service.AirPollutionService;
import com.ssafy.env.model.service.NearInfoService;
import com.ssafy.env.model.service.TrashService;

@RestController
@RequestMapping("/env")
public class EnvController {

  private final AirPollutionService airPollutionService;
  private final NearInfoService nearInfoService;
  private final TrashService trashService;

  public EnvController(AirPollutionService airPollutionService, NearInfoService nearInfoService,
      TrashService trashService) {
    super();
    this.airPollutionService = airPollutionService;
    this.nearInfoService = nearInfoService;
    this.trashService = trashService;
  }

  @GetMapping("/pollution/{dongcode}")
  public ResponseEntity<List<AirPollutionDto>> getPollutionData(
      @PathVariable("dongcode") String dongcode) throws SQLException {
    Map<String, String> pollutionMap = new HashMap<>();
    pollutionMap.put("dongcode", dongcode);
    return ResponseEntity.ok(airPollutionService.getPollutionInfo(pollutionMap));
  }

  @GetMapping("/trashbag/{dongcode}")
  public ResponseEntity<List<TrashDto>> getTrashbagData(@PathVariable("dongcode") String dongcode)
      throws SQLException {
    return ResponseEntity.ok(trashService.getTrashInfo(dongcode));
  }
}
