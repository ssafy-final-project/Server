package com.ssafy.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.ssafy.dashboard.model.AtomicApiMetric;
import com.ssafy.dashboard.model.service.ApiMetricsService;
import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ExecutionTimeLoggerAspect {

  @Autowired
  private ApiMetricsService apiMetricsService;

  private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeLoggerAspect.class);

  @Around("execution(* com.ssafy..*(..))") // 모든 패키지의 joinpoint에 Around AOP
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object proceed = joinPoint.proceed();
    long executionTime = System.currentTimeMillis() - start;

//    logger.info(">>> {} executed in {} ms", joinPoint.getSignature(), executionTime);

    return proceed;
  }

  @Around("execution(* com.ssafy.*.controller..*(..))") // 모든 컨트롤러의 endpoint에 Around AOP
  public Object logEndpointProcessTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object proceed = joinPoint.proceed();
    long executionTime = System.currentTimeMillis() - start;

    // 메서드 정보 추출
    HttpServletRequest request = getCurrentRequest();
    String endpoint = request.getRequestURI().substring(1);
    String method = request.getMethod(); // 요청 방식 추출

    int slice = endpoint.indexOf('/');
    // 응답 시간 저장
    apiMetricsService.record("/" + endpoint.substring(0, slice == -1 ? endpoint.length() : slice), method, executionTime);

    return proceed;
  }

  private HttpServletRequest getCurrentRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }
}
