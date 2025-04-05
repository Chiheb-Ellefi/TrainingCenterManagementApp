package com.example.CenterManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CenterManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CenterManagementApplication.class, args);
	}

}
