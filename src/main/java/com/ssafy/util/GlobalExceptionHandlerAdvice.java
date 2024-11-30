package com.ssafy.util;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {
  // @ExceptionHandler(Throwable.class)
  // public ResponseEntity handleAllExceptions(Exception ex) {
  // Map<String, Object> errorResponse = new HashMap<>();
  // errorResponse.put("error", "Global Error");
  // errorResponse.put("msg", ex.getMessage());
  // return ResponseEntity.internalServerError();
  // }

}
