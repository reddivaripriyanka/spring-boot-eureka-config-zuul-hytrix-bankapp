package com.capgemini.zuulproxy.springbootzuulgatewayproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
@SpringBootApplication
@EnableZuulProxy
public class SpringBootZuulGatewayProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootZuulGatewayProxyApplication.class, args);
	}

}

