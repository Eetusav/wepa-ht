/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.config;

/**
 *
 * @author Matti
 */
import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("heroku")
public class HerokuPostgreSqlConfiguration {

    private static final String HEROKU_DATABASE_URL_KEY = "DATABASE_URL";
    private static final String HEROKU_POSTGRE_URL_PREFIX = "jdbc:postgresql://";
    private static final String POSTGRESQL_DRIVER_CLASS = "org.postgresql.Driver";

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv(HEROKU_DATABASE_URL_KEY));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = HEROKU_POSTGRE_URL_PREFIX + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        PoolProperties pp = new PoolProperties();
        pp.setDriverClassName(POSTGRESQL_DRIVER_CLASS);
        pp.setInitialSize(1);
        pp.setPassword(password);
        pp.setUsername(username);
        pp.setUrl(dbUrl);
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(pp);

        return dataSource;
//        BasicDataSource basicDataSource = new BasicDataSource();
//        basicDataSource.setUrl(dbUrl);
//        basicDataSource.setUsername(username);
//        basicDataSource.setPassword(password);
//
//        return basicDataSource;
    }
}
