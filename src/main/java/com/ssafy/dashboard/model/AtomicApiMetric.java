package com.ssafy.dashboard.model;

import java.util.concurrent.atomic.AtomicLong;

// DTO 아님
public class AtomicApiMetric {
  private final AtomicLong totalResponseTime; // 누적 응답 시간
  private final AtomicLong requestCount; // 요청 횟수
  private volatile double movingAverage; 
  private final double alpha;

  public AtomicApiMetric(long initialResponseTime, long initialCount, int smoothingFactor) {
      this.totalResponseTime = new AtomicLong(initialResponseTime);
      this.requestCount = new AtomicLong(initialCount);
      this.alpha = 2.0 / (smoothingFactor + 1);
      this.movingAverage = initialResponseTime; 
  }

  public void update(long responseTime) {
      totalResponseTime.addAndGet(responseTime);
      requestCount.incrementAndGet();

      // EMA
      movingAverage = alpha * responseTime + (1 - alpha) * movingAverage;
  }

  public double getMovingAverage() {
      return movingAverage;
  }

  public double getAverageResponseTime() {
      return totalResponseTime.doubleValue() / requestCount.doubleValue();
  }

  public long getRequestCount() {
      return requestCount.get();
  }
}