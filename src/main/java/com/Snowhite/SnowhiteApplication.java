package com.Snowhite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@SpringBootApplication
public class SnowhiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnowhiteApplication.class, args);
	}

	@Bean
	public Docket getSwaggerConfig() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.Snowhite"))
				.build()
				.apiInfo(getRestApiInfo());
	}

	private ApiInfo getRestApiInfo() {
		return new ApiInfo("Snowhite REST API Documentation",
				"Full documentation of REST API endpoints",
				"1.0",
				"",
				new Contact("Javid", "https://www.linkedin.com/in/javid-baghir/", "j.baghir01@gmail.com"),
				"",
				"",
				Collections.emptyList());
	}
}
