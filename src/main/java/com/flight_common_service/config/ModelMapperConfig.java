package com.flight_common_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * model mapper configuration for conversion
 *
 */
@Configuration
public class ModelMapperConfig {
	
	
	/**
	 * model mapper bean
	 * @return
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
