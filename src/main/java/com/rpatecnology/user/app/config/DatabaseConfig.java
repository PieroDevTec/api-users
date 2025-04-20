package com.rpatecnology.user.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Bean(name = {"db_source_desa"})
    @Primary
    @ConfigurationProperties(prefix = "desa.datasource")
    public DataSource hostDataSource(){
        return DataSourceBuilder.create().build();
    }
    @Bean(name = {"db_desa_user"})
    public JdbcTemplate hostJdbcTemplate(@Qualifier("db_source_desa") DataSource dbconexion){
        return new JdbcTemplate(dbconexion);
    }
}
