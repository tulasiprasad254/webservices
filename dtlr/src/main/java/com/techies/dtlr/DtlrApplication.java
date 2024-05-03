package com.techies.dtlr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.techies"})
public class DtlrApplication {

	public static void main(String[] args) {
		SpringApplication.run(DtlrApplication.class, args);
	}

}
