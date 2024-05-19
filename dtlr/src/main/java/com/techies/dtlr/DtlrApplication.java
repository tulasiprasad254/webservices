package com.techies.dtlr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.techies.dtlr.entity.CustomerOrders;

@SpringBootApplication(scanBasePackages = "com.techies.dtlr")
@ComponentScan({"com.techies"})
public class DtlrApplication {

	public static void main(String[] args) {
		SpringApplication.run(DtlrApplication.class, args);
	}

	@Bean
    public CustomerOrders customerOrders() {
        return new CustomerOrders();
    }
}
