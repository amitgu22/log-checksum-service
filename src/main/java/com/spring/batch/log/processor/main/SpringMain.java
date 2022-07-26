package com.spring.batch.log.processor.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.spring.batch.log.processor.*")
public class SpringMain {
    public static void main(String[] args) {

        SpringApplication.run(SpringMain.class, args);
    }
}
