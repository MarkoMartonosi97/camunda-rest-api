package com.marko.camundarestapis.handlers;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;

@Service
@ImportResource("classpath*:applicationContext.xml")
public class ProcessStartHandler implements ExecutionListener {
	
	@Autowired
	IdentityService identityService;

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		//System.out.println(identityService == null);
		//System.out.println(execution.getProcessEngineServices().getIdentityService() == null);
		List<User> users =  execution.getProcessEngineServices().getIdentityService().createUserQuery().list();
		if(users != null && !users.isEmpty()) {
			execution.setVariable("users", users);
		}
		execution.setVariable("dalijedoradapotrebna", false);
		execution.setVariable("daliterminodgovara", true);
		execution.setVariable("izbaceniClanKomisije", null);
		//execution.setVariable("customForm1", "embedded:app:forms/loan.html");
		
		
		List<Group> groups = execution.getProcessEngineServices().getIdentityService().createGroupQuery().list();
		for(Group group : groups) {
			List<User> members = execution.getProcessEngineServices().getIdentityService().createUserQuery().memberOfGroup(group.getId()).list();
			execution.setVariable(group.getName(), members);
		}
		
		
		execution.setVariable("rukovodilac", execution.getProcessEngineServices().getIdentityService().createUserQuery().userId("rukovodilac").singleResult());
		execution.setVariable("dekan", execution.getProcessEngineServices().getIdentityService().createUserQuery().userId("dekan").singleResult());
		execution.setVariable("referent", null);
		execution.setVariable("clanoviKomisije", new ArrayList<User>());
		execution.setVariable("diplomski", new String());
		execution.setVariable("studentPolozioDiplomski", true);
		execution.setVariable("experimentalGroup", execution.getProcessEngineServices().getIdentityService().createUserQuery().memberOfGroup("referenti").list());
		execution.setVariable("ocenaDiplomskog", 0);
		execution.setVariable("mailMessage", new String());
		
		//System.out.println("Poceo proces");
//		List<User> users = execution.getProcessEngineServices().getIdentityService().createUserQuery().userIdIn("pera", "mika", "zika").list();
//		if(users.isEmpty() ) {
//			
//			User user1 = execution.getProcessEngineServices().getIdentityService().newUser("pera");
//			user1.setEmail("pera@pera.com");
//			user1.setFirstName("pera");
//			user1.setLastName("pera");
//			user1.setPassword("pera");
//			execution.getProcessEngineServices().getIdentityService().saveUser(user1);
//			
//			User user2 = execution.getProcessEngineServices().getIdentityService().newUser("mika");
//			user2.setEmail("mika@mika.com");
//			user2.setFirstName("mika");
//			user2.setLastName("mika");
//			user2.setPassword("mika");
//			execution.getProcessEngineServices().getIdentityService().saveUser(user2);
//			
//			User user3 = execution.getProcessEngineServices().getIdentityService().newUser("zika");
//			user3.setEmail("zika@zika.com");
//			user3.setFirstName("zika");
//			user3.setLastName("zika");
//			user3.setPassword("zika");
//			
//			execution.getProcessEngineServices().getIdentityService().saveUser(user3);
//			
//			
//			User user4 = execution.getProcessEngineServices().getIdentityService().newUser("dekan");
//			user4.setEmail("dekan@dekan.com");
//			user4.setFirstName("dekan");
//			user4.setLastName("dekan");
//			user4.setPassword("dekan");
//			
//			execution.getProcessEngineServices().getIdentityService().saveUser(user4);
//		}
//		
		
//		System.out.println("Identity service u startnog procesu: ---------------------");
//		System.out.println(identityService == null);
		//execution.setVariable("dekan", "dekan");
		//users = execution.getProcessEngineServices().getIdentityService().createUserQuery().list();
		
		//execution.setVariable("users", users);
		
	}
	
//	public IdentityService getIdentityService() {
//		return identityService;
//	}
//
//	public void setIdentityService(IdentityService identityService) {
//		this.identityService = identityService;
//	}
}
