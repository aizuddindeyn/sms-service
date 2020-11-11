/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.aizuddindeyn.sms"})
@MapperScan("com.aizuddindeyn.sms.mapper")
@EnableCaching
public class SmsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsServiceApplication.class, args);
    }
}
