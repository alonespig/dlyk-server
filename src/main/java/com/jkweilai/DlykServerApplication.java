package com.jkweilai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(basePackages = {"com.jkweilai.mapper"})
@SpringBootApplication
public class DlykServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DlykServerApplication.class, args);
    }

}
