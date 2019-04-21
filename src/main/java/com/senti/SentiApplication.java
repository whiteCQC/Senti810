package com.senti;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.senti.dao")
public class SentiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentiApplication.class, args);


    }


}
