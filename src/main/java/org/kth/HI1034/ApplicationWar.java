package org.kth.HI1034;


import org.kth.HI1034.model.domain.entity.authority.Authority;
import org.kth.HI1034.model.domain.entity.authority.AuthorityRepository;
import org.kth.HI1034.util.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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






	private static final AppKeyFactory apiKeyFactory = new AppKeyFactory();

	public static void main(String[] args) throws Exception{

		SpringApplication.run(ApplicationWar.class, args);

	}


	@Autowired
	private AuthorityRepository authorityRepo;



	public void jpaConfiguration() throws Exception{

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



}
