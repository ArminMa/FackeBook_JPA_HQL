package org.kth.HI1034.security.FilterTest;

import org.kth.HI1034.exceptions.restExeption.RestExceptionCode;
import org.kth.HI1034.model.domain.keyUserServer.UserKeyRepository;
import org.kth.HI1034.model.domain.user.FaceUserRepository;
import org.kth.HI1034.util.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

// todo fix so all this classes in folder FilterTest can filter the @RequestMapping("/api")


@Configuration
@ComponentScan(value = "org.kth.HI1034.model")
@EnableWebSecurity
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public UserKeyRepository userServerKeyRepo;

	@Autowired
	public FaceUserRepository faceUserRepo;

	@Value("${server.secretKey}")
	private String serverSecretKey;

	@Value("${token.header}")
	private String tokenHeader;

	@Value("${user.token.header}")
	private String userTokenHeader;



	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter( userServerKeyRepo, faceUserRepo,  tokenHeader, userTokenHeader));
		registrationBean.addUrlPatterns("/api/*", "/api/**");

		return registrationBean;
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.addFilterBefore(new JwtFilter( userServerKeyRepo, faceUserRepo,  tokenHeader, userTokenHeader), BasicAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(new AuthenticationEntryPoint() {
					@Override
					public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
							throws IOException, ServletException {
						JsonErrorBuilder.buildJsonError(response, RestExceptionCode.FC_RE_001, "authorization required", HttpStatus.UNAUTHORIZED);
					}
				})
				.accessDeniedHandler(new AccessDeniedHandler() {
					@Override
					public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
							throws IOException, ServletException {
						JsonErrorBuilder.buildJsonError(response, RestExceptionCode.FC_RE_001, "Access denied", HttpStatus.FORBIDDEN);
					}
				})
				.and()
				.authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/register").permitAll()
				.antMatchers("/ping/*").permitAll()
				.antMatchers("/getAppPublicKey", "/getAppPublicKey/*").permitAll()
				.antMatchers("/restoration/abandon/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/api*//*", "/api*//**").hasRole(Role.USER)
				.antMatchers(HttpMethod.POST, "/api*//*", "/api*//**").hasAuthority(Role.USER);
//				.anyRequest().hasRole("USER");
	}


//	@Bean
//	Filter authenticationTokenFilterBean() {
//		return new JwtFilter(authService, serverSecretKey, tokenHeader);
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
}