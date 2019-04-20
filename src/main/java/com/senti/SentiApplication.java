package com.senti;

import com.senti.helper.Property;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.senti.dao")
public class SentiApplication {

    public static void main(String[] args) {
        Property p=new Property();
        System.out.println(p.getValue("test"));
        SpringApplication.run(SentiApplication.class, args);


    }


}
