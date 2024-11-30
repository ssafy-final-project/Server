package com.ssafy.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

// Swagger-UI 확인
// http://localhost/swagger-ui/index.html

@Configuration
public class SwaggerConfiguration {

  @Bean
  OpenAPI openAPI() {
    Info info = new Info().title("SSAFY Home API 명세서")
        .description("<h3>Project API Reference for Developers</h3>SSAFY Home API<br>")
        .version("v1").contact(new io.swagger.v3.oas.models.info.Contact().name("ssafy7")
            .email("ssafy7@ssafy.com").url("http://edu.ssafy.com"));

    return new OpenAPI().components(new Components()).info(info);
  }

  @Bean
  GroupedOpenApi allApi() {
    return GroupedOpenApi.builder().group("ALL").pathsToMatch("/**").build();
  }

  @Bean
  GroupedOpenApi noticeApi() {
    return GroupedOpenApi.builder().group("Notice 컨트롤러").pathsToMatch("/notice/**").build();
  }

  @Bean
  GroupedOpenApi memberApi() {
    return GroupedOpenApi.builder().group("Member 컨트롤러").pathsToMatch("/member/**").build();
  }

  @Bean
  GroupedOpenApi envApi() {
    return GroupedOpenApi.builder().group("Env 컨트롤러").pathsToMatch("/env/**").build();
  }

  @Bean
  GroupedOpenApi dashboardApi() {
    return GroupedOpenApi.builder().group("Dashboard 컨트롤러").pathsToMatch("/dashboard/**").build();
  }

  @Bean
  GroupedOpenApi routeApi() {
    return GroupedOpenApi.builder().group("Route 컨트롤러").pathsToMatch("/route/**").build();
  }

  @Bean
  GroupedOpenApi aptApi() {
    return GroupedOpenApi.builder().group("Apt 컨트롤러").pathsToMatch("/apt/**").build();
  }

  @Bean
  GroupedOpenApi qnaApi() {
    return GroupedOpenApi.builder().group("Qna 컨트롤러").pathsToMatch("/qna/**").build();
  }

}
