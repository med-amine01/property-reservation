package com.infor.propertyreservation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI propertyReservationOpenAPI() {
		return new OpenAPI().info(new Info().title("Property Reservation Rest API")
			.description("API documentation for the Property Reservation Platform")
			.version("1.0.0")
			.contact(new Contact().email("m.chebbi.tech@gmail.com")));
	}

}
