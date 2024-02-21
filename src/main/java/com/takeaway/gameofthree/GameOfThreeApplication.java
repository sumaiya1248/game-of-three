package com.takeaway.gameofthree;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GameOfThreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameOfThreeApplication.class, args);
        log.info("Application started");
    }

}
