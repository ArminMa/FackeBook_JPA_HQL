package org.kth.HI1034;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
//import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Sample WAR application
 */
@SpringBootApplication
public class ApplicationWar extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}



	public static void main(String[] args) {
		SpringApplication.run(ApplicationWar.class, args);
	}

}
