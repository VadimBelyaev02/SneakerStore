package com.vadim.sneakerstore.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(basePackages = "com.vadim.sneakerstore")
public class Config {

//    @Bean
//    public SpringLiquibase liquibase() {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
//        liquibase.setDataSource(dataSource());
//        return liquibase;
//    }
}
