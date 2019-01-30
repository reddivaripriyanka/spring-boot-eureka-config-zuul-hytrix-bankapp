package com.capgemini.bankappeurekaservice.Eurekaservicebankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
@EnableEurekaServer
@SpringBootApplication
public class EurekaServiceBankappApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServiceBankappApplication.class, args);
	}

}

