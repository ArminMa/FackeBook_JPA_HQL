package org.kth.HI1034;

import org.kth.HI1034.jwtangspr.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


//import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Sample WAR application
 */
@SpringBootApplication
/*	@ComponentScan
	@EnableAutoConfiguration
	@EnableTransactionManagement
	@Configuration*/
public class ApplicationWar /*extends SpringBootServletInitializer*/ {

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(Application.class);
//	}
	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/api/*");

		return registrationBean;
	}


	public static void main(String[] args) throws Exception{
		SpringApplication.run(ApplicationWar.class, args);
	}



}
