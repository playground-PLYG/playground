package com.playground.config;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {
  private static final String MASTER_DATA_SOURCE = "masterDataSource";
  private static final String SLAVE_DATA_SOURCE = "slaveDataSource";

  @Bean(MASTER_DATA_SOURCE)
  @ConfigurationProperties(prefix = "spring.datasource.master.hikari")
  DataSource masterDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean(SLAVE_DATA_SOURCE)
  @ConfigurationProperties(prefix = "spring.datasource.slave.hikari")
  DataSource slaveDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }
}
