package com.yosekit.creditmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class CreditManagerApplication {

	public static void main(String[] args) {
        SpringApplication.run(CreditManagerApplication.class, args);
	}

}
