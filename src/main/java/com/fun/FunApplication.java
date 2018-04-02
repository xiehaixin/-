package com.fun;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;

@SpringBootApplication
@ServletComponentScan
public class FunApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(FunApplication.class, args);
	}
	
	@Autowired  
    private Environment env;  
  
    @Bean  
    public DataSource dataSource() {  
        DruidDataSource dataSource = new DruidDataSource();  
        dataSource.setUrl(env.getProperty("spring.datasource.url"));  
        dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名  
        dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码  
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));  
        dataSource.setInitialSize(2);  
        dataSource.setMaxActive(20);  
        dataSource.setMinIdle(0);  
        dataSource.setMaxWait(60000);  
        dataSource.setValidationQuery("SELECT 1");  
        dataSource.setTestOnBorrow(false);  
        dataSource.setTestWhileIdle(true); 
        dataSource.setPoolPreparedStatements(false);
        return dataSource;  
    }  
    
    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }*/
    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(DemoApplication.class);
    }*/
    
}
