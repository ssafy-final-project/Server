package com.ssafy.route.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.ssafy.apt.model.AptDto;
import com.ssafy.apt.model.DongStatDto;
import com.ssafy.apt.model.DongcodeDto;
import com.ssafy.apt.model.service.StatService;
import com.ssafy.apt.model.service.StatServiceImpl;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.mapper.MemberMapper;
import com.ssafy.route.model.RouteDto;
import com.ssafy.route.model.mapper.RouteMapper;
import com.ssafy.util.Encryptor;

@Service
@DependsOn("statServiceImpl")
public class RouteServiceImpl implements RouteService {
  private RouteMapper mapper;
  private StatService statService;

  private List<RouteDto> routeList;
  private Map<String, List<Edge>> graph;

  // Initialization
  public RouteServiceImpl(RouteMapper mapper, StatService statService) {
    this.mapper = mapper;
    this.statService = statService;

    try {
      List<DongStatDto> vertices = this.statService.getDongStatAll();

      this.routeList = this.mapper.getRouteAll();

      this.graph = new HashMap<>();
      for (int i = 0; i < vertices.size(); i++) {
        DongStatDto cur = vertices.get(i);
        this.graph.put(cur.getDongCode(), new ArrayList<>());
      }

      for (int i = 0; i < this.routeList.size(); i++) {
        RouteDto cur = this.routeList.get(i);
        Double weight =
            cur.getDurationWeight() == null ? Integer.MAX_VALUE : cur.getDurationWeight();
        this.graph.get(cur.getNodeFrom()).add(new Edge(cur.getNodeTo(), weight));
        this.graph.get(cur.getNodeTo()).add(new Edge(cur.getNodeFrom(), weight));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<RouteDto> findRouteDijkstra(String dongcode) throws Exception {
    List<RouteDto> result = new ArrayList<>();

    PriorityQueue<Edge> pq =
        new PriorityQueue<>((o1, o2) -> Double.compare(o1.durationWeight, o2.durationWeight));
    Map<String, Double> distances = new HashMap<>();

    for (String key : graph.keySet()) {
      distances.put(key, Double.MAX_VALUE);
    }
    distances.put(dongcode, 0.0);
    pq.add(new Edge(dongcode, 0.0));

    while (!pq.isEmpty()) {
      Edge current = pq.poll();
      String currentNode = current.to;
      double currentWeight = current.durationWeight;

      for (Edge edge : graph.getOrDefault(currentNode, Collections.emptyList())) {
        double newDist = currentWeight + edge.durationWeight;

        if (newDist < 7200 && newDist < distances.get(edge.to)) {
          distances.put(edge.to, newDist);
          pq.add(new Edge(edge.to, newDist));
        }
      }
    }

    for (String key : distances.keySet()) {
      if (!dongcode.equals(key) && distances.get(key) < 7200) {
        double weight = distances.get(key);
        result.add(new RouteDto(dongcode, key, weight));
      }
    }

    System.out.println(result.size());
    return result;
  }

  private static class Edge {
    public String to;
    public double durationWeight;

    public Edge(String to, double durationWeight) {
      super();
      this.to = to;
      this.durationWeight = durationWeight;
    }
  }
}
