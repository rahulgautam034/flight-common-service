package com.flight_common_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * main class
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FlightCommonServiceApplication {

	/**
	 * main method
	 *
	 */
	public static void main(final String[] args) {
		SpringApplication.run(FlightCommonServiceApplication.class, args);
	}

}
