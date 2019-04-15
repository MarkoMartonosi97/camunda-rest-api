package com.marko.camundarestapis.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormFieldValidationConstraint;
import org.camunda.bpm.engine.form.FormProperty;
import org.camunda.bpm.engine.form.FormType;
import org.camunda.bpm.engine.form.StartFormData;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.impl.form.type.FormTypes;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.type.ValueType;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.stereotype.Service;

@Service
public class RukovodilacBiraNoveClanoveKomisijeHandler implements TaskListener {
	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution processInstanceCtx = delegateTask.getExecution();
		//List<String> clanoviZaIzbacivanje = (List<String>)processInstanceCtx.getVariable("izbaceniClanoviKomisije");
		System.out.println("Forma: " + processInstanceCtx.getVariable("customForm1"));
		//processInstanceCtx.getProcessEngineServices().getFormService().getform
		//		for(String x : clanoviZaIzbacivanje) {
//			System.out.println("Izbaci ovog: " + x);
//		}
		TaskFormData tfd = delegateTask.getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
//		List<FormField> fields = tfd.getFormFields();
//		for(FormField f : fields) {
//			System.out.println("Field:" + f.getLabel() + ":" + f.getValue().getValue());
//			fields.remove(f);
//		}
		
		
		
		List<FormField> fields = tfd.getFormFields();
		  for(FormField f:fields) {
			  if((f.getTypeName().equals("enum") && f.getId().equals("noviClanKomisije"))) {
				  EnumFormType enumFormType = (EnumFormType) f.getType();
				  Map<String, String> values = enumFormType.getValues();
				  List<User> users =  delegateTask.getProcessEngineServices().getIdentityService().createUserQuery().memberOfGroup("profesori").list();
				  List<User> clanoviKomisije =  (List<User>)processInstanceCtx.getVariable("clanoviKomisije");
//				  List<User> filteredUsers = new ArrayList<User>();
//				  for(User user : users) {
//					  for(User user2 : clanoviKomisije) {
//						  if(!user.getId().equals(user2.getId())) {
//							  filteredUsers.add(user);
//						  }
//					  }
//				  }
				  
				  String izbaceniClanId =  (String) processInstanceCtx.getVariable("izbaceniClanKomisije");
				  
				  List<User> updatedClanoviKomisije = new ArrayList<User>(clanoviKomisije);
				  String clanZaIzbacivanje = processInstanceCtx.getProcessEngineServices().getIdentityService().createUserQuery().userId(izbaceniClanId).singleResult().getId();
				  for(User u : updatedClanoviKomisije) {
					  if(u.getId().equals(clanZaIzbacivanje)) {
						  updatedClanoviKomisije.remove(u);
					  }
				  }
				  
				  processInstanceCtx.setVariable("clanoviKomisije", updatedClanoviKomisije);
				  
				  String mentorId = ((User)processInstanceCtx.getVariable("mentor")).getId();
				  for(User user : users) {
					  if(!clanoviKomisije.contains(user) && 
							  !user.getId().equals(izbaceniClanId) &&
							  !user.getId().equals(mentorId)) {
						  values.put(user.getId(), user.getFirstName() + " " + user.getLastName());
					  }
				  }
			  }
		  }
	}
}
