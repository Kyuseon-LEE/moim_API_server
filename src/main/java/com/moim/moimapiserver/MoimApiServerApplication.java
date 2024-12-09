package com.moim.moimapiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoimApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoimApiServerApplication.class, args);
    }

}
