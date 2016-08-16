package org.kth.HI1034;


import org.kth.HI1034.security.util.CipherUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Sample WAR application
 */
@SpringBootApplication
//@ComponentScan(basePackages = "org")
//@EnableAutoConfiguration
//	@EnableTransactionManagement

public class ApplicationWar /*extends SpringBootServletInitializer*/ {

	private static final AppKeyFactory apiKeyFactory = new AppKeyFactory();
	private static final CipherUtils cipherUtil = new CipherUtils();




	public static void main(String[] args) throws Exception{

		SpringApplication.run(ApplicationWar.class, args);

	}









/*

	@Autowired
	private AuthorityRepository authorityRepo;



	public void jpaConfiguration() {

		Authority authority = authorityRepo.findOne(1L);
		if(authority == null){
			authority = new Authority(Role.ROLE_USER);
			authorityRepo.save(authority);
			authorityRepo.flush();
		}

		authority = authorityRepo.findOne(2L);
		if(authority != null){
			authority = new Authority(Role.ROLE_ADMIN);
			authorityRepo.save(authority);
			authorityRepo.flush();
		}

		authority = authorityRepo.findOne(3L);
		if(authority != null){
			authority = new Authority(Role.ROLE_SUPER_ADMIN);
			authorityRepo.save(authority);
			authorityRepo.flush();
		}

	}

*/


}
