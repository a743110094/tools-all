package site.heaven96.example.config;


import cn.beecp.BeeDataSource;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.ds.bee.BeeDSFactory;
import cn.hutool.db.ds.tomcat.TomcatDSFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DsConfig {
    @Bean("master")
    public DataSource getSource(){
        BeeDataSource beeDataSource = new BeeDataSource();
        return beeDataSource;
    }
}
