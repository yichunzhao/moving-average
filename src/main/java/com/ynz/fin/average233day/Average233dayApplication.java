package com.ynz.fin.average233day;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Average233dayApplication {

	public static void main(String[] args) {
		log.info("cal 233 average ");
		SpringApplication.run(Average233dayApplication.class, args);
	}

}
