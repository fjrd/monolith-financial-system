package com.example.monolith_financial_system;

import org.springframework.boot.SpringApplication;

public class TestMonolithFinancialSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(MonolithFinancialSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
