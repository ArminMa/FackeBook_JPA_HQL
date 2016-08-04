package org.kth.HI1034.configuration.db;


import org.apache.commons.dbcp2.BasicDataSource;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.kth.HI1034.util.enums.DbProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;

import javax.sql.DataSource;

@Configuration
@PropertySource(value="classpath:/mysql.properties")
@Profile(DbProfile.MYSQL)
public class MySqlConfig extends JpaCommonConfig {


    @Override
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(getDriverClassName());
        dataSource.setUrl(getUrl());
        dataSource.setUsername(getUser());
        dataSource.setPassword(getPassword());
        dataSource.setValidationQuery(getDatabaseValidationQuery());
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(false);
        dataSource.setTimeBetweenEvictionRunsMillis(1800000);
        dataSource.setNumTestsPerEvictionRun(3);
        dataSource.setMinEvictableIdleTimeMillis(1800000);
        return dataSource;
    }



    @Override
    protected Class<? extends Dialect> getDatabaseDialect() {
        return MySQL5InnoDBDialect.class;
    }

    @Bean
    public DatabasePopulator databasePopulator() {
        return null;
    }

}

