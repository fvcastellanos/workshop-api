package net.cavitos.workshop.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "net.cavitos.workshop.model.repository")
public class DataSourceConfiguration {
    
    @Bean
    public DataSource dataSource(@Value("${spring.datasource.url}") final String url) {

        final var configuration = new HikariConfig();
        configuration.setJdbcUrl(url);


        return new HikariDataSource(configuration);

    }
}
