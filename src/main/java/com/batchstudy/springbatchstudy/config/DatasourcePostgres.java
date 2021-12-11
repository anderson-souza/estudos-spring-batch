package com.batchstudy.springbatchstudy.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.batchstudy.springbatchstudy.system2",
    entityManagerFactoryRef = "postgresEntityManagerFactory",
    transactionManagerRef= "postgresTransactionManager"
)
public class DatasourcePostgres {

    @Bean
    @ConfigurationProperties("app.datasource.postgres")
    public DataSourceProperties postgresDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.postgres.configuration")
    public HikariDataSource postgrsDatasource() {
        return postgresDatasourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "postgresEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
        EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(postgrsDatasource())
            .packages("com.batchstudy.springbatchstudy.system2")
            .build();
    }

    @Bean
    public PlatformTransactionManager postgresTransactionManager(
        final @Qualifier("postgresEntityManagerFactory") LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory) {
        return new JpaTransactionManager(postgresEntityManagerFactory.getObject());
    }

}
