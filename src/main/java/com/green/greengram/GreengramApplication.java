package com.green.greengram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // auditing 기능 활성화
@SpringBootApplication
@ConfigurationPropertiesScan //
public class GreengramApplication {
    public static void main(String[] args) {
        SpringApplication.run(GreengramApplication.class, args);
    }
}
