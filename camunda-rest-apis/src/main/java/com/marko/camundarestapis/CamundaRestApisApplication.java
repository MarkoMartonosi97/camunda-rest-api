package com.marko.camundarestapis;

import java.util.List;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@SpringBootApplication
@ImportResource("classpath*:applicationContext.xml")
public class CamundaRestApisApplication {

	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private ManagementService ms;
	
	public static void main(String[] args) {
		SpringApplication.run(CamundaRestApisApplication.class, args);
	}
	
	
	@PostConstruct
	public void initUsers() {
		System.out.println("Identity service on startup----------------------");
		
		
		List<Group> groups = identityService.createGroupQuery().groupIdIn("profesori", "referenti").list();
		if(groups.isEmpty()) {
			Group profesori = identityService.newGroup("profesori");
			profesori.setName("profesori");
			profesori.setType("WORKFLOW");
			identityService.saveGroup(profesori);
			
			Group referenti = identityService.newGroup("referenti");
			referenti.setName("referenti");
			referenti.setType("WORKFLOW");
			identityService.saveGroup(referenti);
		}
		
		User rukovodilac = identityService.createUserQuery().userId("rukovodilac").singleResult();
		if(rukovodilac == null) {
			rukovodilac = identityService.newUser("rukovodilac");
			rukovodilac.setEmail("rukovodilac@gmail.com");
			rukovodilac.setFirstName("rukovodilac");
			rukovodilac.setLastName("rukovodilac");
			rukovodilac.setPassword("rukovodilac");
			identityService.saveUser(rukovodilac);
		}
		
		User dekan = identityService.createUserQuery().userId("dekan").singleResult();
		if(dekan == null) {
			dekan = identityService.newUser("dekan");
			dekan.setEmail("dekan@gmail.com");
			dekan.setFirstName("dekan");
			dekan.setLastName("dekan");
			dekan.setPassword("dekan");
			identityService.saveUser(dekan);
		}
		
		User student = identityService.createUserQuery().userId("student").singleResult();
		if(student == null) {
			student = identityService.newUser("student");
			student.setEmail("student@gmail.com");
			student.setFirstName("student");
			student.setLastName("student");
			student.setPassword("student");
			identityService.saveUser(student);
		}
		
		List<User> profesori = identityService.createUserQuery().userIdIn("prof1", "prof2", "prof3", "prof4").list();
		if(profesori.isEmpty() ) {
			User prof1 = identityService.newUser("prof1");
			prof1.setEmail("prof1@gmail.com");
			prof1.setFirstName("prof1");
			prof1.setLastName("prof1");
			prof1.setPassword("prof1");
			identityService.saveUser(prof1);
			identityService.createMembership(prof1.getId(), "profesori");
			
			
			User prof2 = identityService.newUser("prof2");
			prof2.setEmail("prof2@gmail.com");
			prof2.setFirstName("prof2");
			prof2.setLastName("prof2");
			prof2.setPassword("prof2");
			identityService.saveUser(prof2);
			identityService.createMembership(prof2.getId(), "profesori");
			
			User prof3 = identityService.newUser("prof3");
			prof3.setEmail("prof3@gmail.com");
			prof3.setFirstName("prof3");
			prof3.setLastName("prof3");
			prof3.setPassword("prof3");
			identityService.saveUser(prof3);
			identityService.createMembership(prof3.getId(), "profesori");
			
			User prof4 = identityService.newUser("prof4");
			prof4.setEmail("prof4@gmail.com");
			prof4.setFirstName("prof4");
			prof4.setLastName("prof4");
			prof4.setPassword("prof4");
			identityService.saveUser(prof4);
			identityService.createMembership(prof4.getId(), "profesori");
		}
		
		List<User> referenti = identityService.createUserQuery().userIdIn("referent1", "referent2", "referent3").list();
		if(referenti.isEmpty() ) {
			User referent1 = identityService.newUser("referent1");
			referent1.setEmail("referent1@gmail.com");
			referent1.setFirstName("referent1");
			referent1.setLastName("referent1");
			referent1.setPassword("referent1");
			identityService.saveUser(referent1);
			identityService.createMembership(referent1.getId(), "referenti");
			
			User referent2 = identityService.newUser("referent2");
			referent2.setEmail("referent2@gmail.com");
			referent2.setFirstName("referent2");
			referent2.setLastName("referent2");
			referent2.setPassword("referent2");
			identityService.saveUser(referent2);
			identityService.createMembership(referent2.getId(), "referenti");
			
			User referent3 = identityService.newUser("referent3");
			referent3.setEmail("referent3@gmail.com");
			referent3.setFirstName("referent3");
			referent3.setLastName("referent3");
			referent3.setPassword("referent3");
			identityService.saveUser(referent3);
			identityService.createMembership(referent3.getId(), "referenti");
		}	
	}
	
	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}

}
