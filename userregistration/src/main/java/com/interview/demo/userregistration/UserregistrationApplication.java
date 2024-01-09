package com.interview.demo.userregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UserregistrationApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserregistrationApplication.class, args
		);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
