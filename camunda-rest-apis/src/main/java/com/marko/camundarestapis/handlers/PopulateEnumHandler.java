package com.marko.camundarestapis.handlers;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PopulateEnumHandler implements TaskListener {
	
	  @Autowired 
	  FormService formService;
	  
	  public void notify(DelegateTask delegateTask) {
		  //System.out.println("POPULATE ENUM -------------------------------------");
		  //System.out.println("Kreiran task:"+delegateTask.getId());
		  
		  String taskId = delegateTask.getId();
		  TaskFormData tfd = delegateTask.getProcessEngineServices().getFormService().getTaskFormData(taskId);
		  List<FormField> fields = tfd.getFormFields();
		  for(FormField f:fields) {
			  if((f.getTypeName().equals("enum") && f.getId().equals("nastavnikDiplomskog")) || 
					  (f.getTypeName().equals("enum") && f.getId().equals("clanKomisije1")) ||
					  (f.getTypeName().equals("enum") && f.getId().equals("clanKomisije2"))) {
				  EnumFormType enumFormType = (EnumFormType) f.getType();
				  Map<String, String> values = enumFormType.getValues();
//				  for(String key:values.keySet()) {
//					  //System.out.println("KEY: "+key+" / "+values.get(key));
//				  }
				  
				  List<User> users =  delegateTask.getProcessEngineServices().getIdentityService().createUserQuery().list();
				  for(User user : users) {
					  Group userGroup = delegateTask.getProcessEngineServices().getIdentityService().createGroupQuery().groupMember(user.getId()).singleResult();
					  if(userGroup != null) {
						  //System.out.println("User group name" + userGroup.getName());
						  if(userGroup.getName().equals("profesori")) {
							  values.put(user.getId(), user.getFirstName() + " " + user.getLastName());
						  }
					  }
					  
					  
				  }
				  
				  
//				  values.put("prva", "Prva opcija");
//				  values.put("druga", "Druga opcija");
//				  values.put("treca", "Treca opcija");
				  
			  } else if(f.getTypeName().equals("enum") && f.getId().equals("studijskaGrupa")) {
				  EnumFormType enumFormType = (EnumFormType) f.getType();
				  Map<String, String> values = enumFormType.getValues();
//				  for(String key:values.keySet()) {
//					  System.out.println("KEY: "+key+" / "+values.get(key));
//				  }
				  values.put("E", "Elektrotehnika i racunarstvo");
				  values.put("M", "Masinstvo");
			  } else if(f.getTypeName().equals("enum") && f.getId().equals("studijskiProgram")) {
				  EnumFormType enumFormType = (EnumFormType) f.getType();
				  Map<String, String> values = enumFormType.getValues();
//				  for(String key:values.keySet()) {
//					  System.out.println("KEY: "+key+" / "+values.get(key));
//				  }
				  values.put("SIT", "Softverske informatione teh.");
				  values.put("SIIT", "Softversko inz. i inf. teh.");
				  values.put("RA", "Racunarstvo i automatika");
			  } else if (f.getTypeName().equals("enum") && f.getId().equals("vrstaStudija")) {
				  EnumFormType enumFormType = (EnumFormType) f.getType();
				  Map<String, String> values = enumFormType.getValues();
//				  for(String key:values.keySet()) {
//					  System.out.println("KEY: "+key+" / "+values.get(key));
//				  }
				  values.put("akademske", "Akademski");
				  values.put("strukovne", "Strukovne");
			  }
		  }
	  }
}