package com.ssafy.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan(basePackages = {"com.ssafy.**.mapper"})
public class DataBaseConfiguration {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.hikari")
  HikariConfig hikariConfig() {
    return new HikariConfig();
  }

  @Bean
  DataSource dataSource() throws Exception {
    DataSource dataSource = new HikariDataSource(hikariConfig());
    return dataSource;
  }

}
