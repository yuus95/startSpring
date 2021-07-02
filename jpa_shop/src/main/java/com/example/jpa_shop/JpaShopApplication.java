package com.example.jpa_shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class JpaShopApplication {
	public static void main(String[] args) {
		SpringApplication.run(JpaShopApplication.class, args);


	}
	@Bean
	Hibernate5Module hibernate5Module(){
		return new Hibernate5Module();
	}
}
