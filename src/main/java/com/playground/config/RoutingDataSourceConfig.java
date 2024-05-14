package com.playground.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import com.playground.constants.DataSourceType;

@EnableJpaRepositories(basePackages = "com.playground.api.*.repository",
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.playground.api.*.repository.redis.*"))
@Configuration
public class RoutingDataSourceConfig {

  private static final String ROUTING_DATA_SOURCE = "routingDataSource";
  private static final String MASTER_DATA_SOURCE = "masterDataSource";
  private static final String SLAVE_DATA_SOURCE = "slaveDataSource";
  private static final String DATA_SOURCE = "dataSource";
  private static final String ENTITY_MANAGER_FACTORY = "entityManagerFactory";
  private static final String TRANSACTION_MANAGER = "transactionManager";

  @Bean(ROUTING_DATA_SOURCE)
  public DataSource routingDataSource(@Qualifier(MASTER_DATA_SOURCE) final DataSource masterDataSource,
      @Qualifier(SLAVE_DATA_SOURCE) final DataSource slaveDataSource) {
    Map<Object, Object> dataSourceMap = new HashMap<>();

    dataSourceMap.put(DataSourceType.MASTER, masterDataSource);
    dataSourceMap.put(DataSourceType.SLAVE, slaveDataSource);

    RoutingDataSource routingDataSource = new RoutingDataSource();

    routingDataSource.setTargetDataSources(dataSourceMap);
    routingDataSource.setDefaultTargetDataSource(masterDataSource);

    return routingDataSource;
  }

  @Primary
  @Bean(DATA_SOURCE)
  public DataSource dataSource(@Qualifier(ROUTING_DATA_SOURCE) DataSource routingDataSource) {
    return new LazyConnectionDataSourceProxy(routingDataSource);
  }

  @Bean(ENTITY_MANAGER_FACTORY)
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier(DATA_SOURCE) DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

    entityManagerFactory.setDataSource(dataSource);
    entityManagerFactory.setPackagesToScan("com.playground.api.*.entity");
    entityManagerFactory.setJpaVendorAdapter(this.jpaVendorAdapter());
    entityManagerFactory.setPersistenceUnitName("entityManager");

    return entityManagerFactory;
  }

  private JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();

    hibernateJpaVendorAdapter.setGenerateDdl(false);
    hibernateJpaVendorAdapter.setShowSql(false);
    hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");

    return hibernateJpaVendorAdapter;
  }

  @Bean(TRANSACTION_MANAGER)
  public PlatformTransactionManager platformTransactionManager(
      @Qualifier(ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean entityManagerFactory) {
    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();

    jpaTransactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

    return jpaTransactionManager;
  }
}
