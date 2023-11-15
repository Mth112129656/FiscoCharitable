package org.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
//配置过滤器
@ServletComponentScan(value = {"org.example.demo.filter"})
//配置事务注解
@EnableTransactionManagement
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
