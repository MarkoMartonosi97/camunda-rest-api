package com.marko.camundarestapis.handlers;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

@Service
public class RukovodilacOdabraoNoveClanoveKomisijeHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		
		DelegateExecution processInstanceCtx = delegateTask.getExecution();
		  String taskId = delegateTask.getId();
		  TaskFormData tfd = delegateTask.getProcessEngineServices().getFormService().getTaskFormData(taskId);
		  List<FormField> fields = tfd.getFormFields();
		  //System.out.println(fields.isEmpty());
		  for(FormField f:fields) {
			  if(f.getId().equals("noviClanKomisije")) {
				  String noviClanId = (String) f.getValue().getValue();
				  User noviClan = processInstanceCtx.getProcessEngineServices().getIdentityService().createUserQuery().userId(noviClanId).singleResult();
				  List<User> clanoviKomisije = (List<User>) processInstanceCtx.getVariable("clanoviKomisije");
				  clanoviKomisije.add(noviClan);
				  
				  for(User u : clanoviKomisije) {
					  System.out.println("Novi clanovi komisije:" + u.getId());
				  }
				  processInstanceCtx.setVariable("clanoviKomisije", clanoviKomisije);
			  }
		  }
		
	}

	
}
