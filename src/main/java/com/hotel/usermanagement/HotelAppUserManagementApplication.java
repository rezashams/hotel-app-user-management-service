package com.hotel.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//TODO delete this annotation
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class HotelAppUserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelAppUserManagementApplication.class, args);
	}

}
