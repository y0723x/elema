package com.yx.elema;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yx.elema.mapper")
public class ElemaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElemaServerApplication.class, args);
    }

}
