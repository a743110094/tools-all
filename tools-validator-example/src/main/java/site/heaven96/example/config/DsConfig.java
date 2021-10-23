package site.heaven96.example.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DsConfig {
    @Bean
    public DataSource build(){
        return new HikariDataSource();
    }
}
