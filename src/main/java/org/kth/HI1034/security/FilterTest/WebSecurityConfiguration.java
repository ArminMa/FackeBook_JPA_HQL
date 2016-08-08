package org.kth.HI1034.security.FilterTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// todo fix so all this classes in folder FilterTest can filter the @RequestMapping("/api")

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {



	@Value("${server.secretKey}")
	private String serverSecretKey;

	@Value("${token.header}")
	private String tokenHeader;

	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter( serverSecretKey, tokenHeader));
		registrationBean.addUrlPatterns("/api/*");

		return registrationBean;
	}



	@Override
	public void configure(WebSecurity webSecurity) throws Exception
	{
		webSecurity.ignoring().antMatchers(HttpMethod.GET, "/user", "/user/*" , "/user/**");
		webSecurity.ignoring().antMatchers(HttpMethod.POST, "/user", "/user/*" , "/user/**", "/getAppPublicKey");


	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.addFilterBefore(tokenAuthorizationFilter(), BasicAuthenticationFilter.class);
		http.authorizeRequests().antMatchers("/api/**").authenticated();
		http.addFilter(tokenAuthorizationFilter());
	}

	private JwtFilter tokenAuthorizationFilter()
	{
		return new JwtFilter( serverSecretKey, tokenHeader);
	}


//	@Bean
//	Filter authenticationTokenFilterBean() {
//		return new JwtFilter(authService, serverSecretKey, tokenHeader);
//	}

//	@Bean
//	public FilterRegistrationBean ApplicationWarFilter()
//	{
//		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		registrationBean.setFilter(filterBeanClass());
//
//		registrationBean.addUrlPatterns("/api/*");
//		registrationBean.addUrlPatterns("/api/**");
//		registrationBean.addUrlPatterns("api/*");
//		registrationBean.addUrlPatterns("api/**");
//		registrationBean.setEnabled(true);
//
//		registrationBean.setOrder(1);
//
//		return registrationBean;
//	}





//	@Override
//	public void configure(HttpSecurity httpSecurity) throws Exception {
//
////		http.csrf().and().addFilter(new JwtFilter());
//		httpSecurity
//
//				// we don't need CSRF because our token is invulnerable
//				.csrf().disable()
//
//
//				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//
//				// don't create session
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//
//				.authorizeRequests()
//				.antMatchers(HttpMethod.GET, "api*//**", "api*//*", "/api*//*", "/api*//**").hasRole(Role.USER)
//				.antMatchers(HttpMethod.POST, "api*//**", "api*//*", "/api*//*", "/api*//**").hasAuthority(Role.USER)
//				.antMatchers(
//						HttpMethod.GET,
//						"/",
//						"*//*.html",
//						"/favicon.ico",
//						"*//***/*//*.html",
//						"*//***/*//*.css",
//						"*//***/*//*.js"
//				).permitAll()
//				.antMatchers("/auth*//**").permitAll()
//				.anyRequest().authenticated()
////
//
//
//				// Custom JWT based security filter
//				.and().addFilterBefore(authenticationTokenFilterBean(), JwtFilter.class);
//
//
//		// disable page caching
//		httpSecurity.headers().cacheControl();
//	}
//
//	@Override
//	public void configure(WebSecurity webSecurity) throws Exception
//	{
//		webSecurity.ignoring()
//				.antMatchers(HttpMethod.GET, "/ping/*")
//				.antMatchers(HttpMethod.POST, "/ping/*")
//				.antMatchers(HttpMethod.POST, "/user/*");
//	}
//
//	@Override
//	public void configure(HttpSecurity httpSecurity) throws Exception
//	{
//		httpSecurity.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
//
//	}
//
////	BasicAuthenticationFilter.class
////	UsernamePasswordAuthenticationFilter.class
//
//	@Bean
//	public FilterRegistrationBean wicketFilterRegistration() {
//
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		JwtFilter jwtFilter = new JwtFilter();
//		registration.setFilter(jwtFilter);
//		registration.setName("JwtFilter");
//		registration.addInitParameter("configuration", configuration);
//		registration.addInitParameter("testsMode", String.valueOf(testMode));
//		registration.addInitParameter("mockMode",String.valueOf(mockMode));
//	 registrationBean.setOrder(2);
//		registration.addUrlPatterns("/portal/*");
//		registration.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.FORWARD);
//		registration.setMatchAfter(true);
//
//
//		return registration;
//	}
}