package com.msrk.es.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



/**
 * @author mdsarfarazkhan
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private final static String basePackage = "com.msrk.es.controller";
	private final static String detailDescription = "Entity Service API docs";
	
	
	

	@Bean
	public Docket entityDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
		.groupName("entity")
		.apiInfo(apiInfo())
		.select()
		.apis(RequestHandlerSelectors.basePackage(basePackage))
		.paths(PathSelectors.regex("/entity.*"))
		.paths(PathSelectors.any())
		.build();
	}
	
	
	@Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
            .displayRequestDuration(true)
            .validatorUrl("")
            .build();
    }
	
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	            .title("Overview")
	            .description(detailDescription)
	            .contact(new Contact("Sarfaraz Khan", "https://www.linkedin.com/in/mdsarfarazkhan/", "mdsarfarazkhan@test.com"))
	            .version("1.0")
	            .build();
	}
}
