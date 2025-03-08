package com.xworkz.userapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;
@Configuration
@ComponentScan("com.xworkz.userapp")
public class SpringConfiguration {
    public SpringConfiguration(){
        System.out.println("Spring configuration constructor is invoked");
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setPackagesToScan("com.xworkz.userapp.entity");
        bean.setDataSource(getDataSource());
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        bean.setJpaProperties(getProperties());
        return bean;
    }

    private Properties getProperties(){
        Properties properties = new Properties();
        properties.setProperty("show_sql","true");
        properties.setProperty("hbm2ddl.auto","update");
        return properties;
    }

    @Bean
    public DataSource getDataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/sys");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("Keerthi8088169847");
        return driverManagerDataSource;

    }
}