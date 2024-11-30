package com.ssafy.util;

import java.util.*;
import org.springframework.stereotype.Component;
import com.ssafy.apt.model.DongStatDto;

@Component
public class MSTBuilder {
  public static List<Object[]> main(List<DongStatDto> pts) {
    int vertices = pts.size();
    double[][] graph = new double[vertices][vertices];

    double lim = 1.0;
    
    for(int i = 0; i < vertices; i++) {
      for(int j = 0; j < vertices; j++) {
        double d = dist(pts.get(i).getLatitude(), pts.get(i).getLongitude(),
            pts.get(j).getLatitude(), pts.get(j).getLongitude());
        if(i != j && d <= lim) {
          graph[i][j] = d;
        } else {
          graph[i][j] = Integer.MAX_VALUE;
          graph[i][j]++;
        }
      }
    }
    
    System.out.println("GRAPH BUILD COMPLETE");
    
    return primMST(graph, vertices);
  }
  
  private static double dist(double ax, double ay, double bx, double by) {
    return (ax - bx) * (ax - bx) + (ay - by) * (ay - by);
  }

  public static List<Object[]> primMST(double[][] graph, int V) {
    boolean[] base = new boolean[V];
    PriorityQueue<Object[]> pq = new PriorityQueue<>((o1, o2) -> Double.compare((double)o1[2], (double)o2[2]));
    List<Object[]> edges = new ArrayList<>();

    for(int i = 0; i < V; i++) {
      pq.add(new Object[] {0, i, graph[0][i]});
    }
    base[0] = true;

    while (!pq.isEmpty() && edges.size() < V - 1) {
      Object[] cur = pq.poll();
      int to = (int)cur[1];
     
      if (base[to]) 
        continue;

      edges.add(cur);
      for(int i = 0; i < V; i++)
        if (!base[i] && graph[to][i] < Integer.MAX_VALUE)
          pq.add(new Object[] {to, i, graph[to][i]});
      
      base[to] = true;
      
      if(edges.size() % 100 == 0) {
        System.out.println(edges.size() + " edges contained... pq size : " + pq.size());
      }
    }

    return edges;
  }
}
