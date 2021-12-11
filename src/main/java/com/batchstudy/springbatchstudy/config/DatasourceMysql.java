package com.batchstudy.springbatchstudy.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.batchstudy.springbatchstudy.system1",
    entityManagerFactoryRef = "mySqlEntityManagerFactory",
    transactionManagerRef= "mysqlTransactionManager"
)
public class DatasourceMysql {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.mysql")
    public DataSourceProperties mysqlDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.mysql.configuration")
    public HikariDataSource mysqlDataSource() {
        return mysqlDatasourceProperties().initializeDataSourceBuilder()
            .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "mySqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
        EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(mysqlDataSource())
            .packages("com.batchstudy.springbatchstudy.system1")
            .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager mysqlTransactionManager(
        final @Qualifier("mySqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory) {
        return new JpaTransactionManager(mysqlEntityManagerFactory.getObject());
    }

}
