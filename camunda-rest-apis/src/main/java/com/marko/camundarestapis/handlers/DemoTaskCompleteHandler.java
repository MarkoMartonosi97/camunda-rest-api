package com.marko.camundarestapis.handlers;

import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoTaskCompleteHandler implements TaskListener {
	
	@Autowired 
	FormService formService;
//	@Autowired
//	DemoDataRepository demoRepository;
	  
	public void notify(DelegateTask delegateTask) {
		  DelegateExecution processInstanceCtx = delegateTask.getExecution();
		  String taskId = delegateTask.getId();
		  TaskFormData tfd = delegateTask.getProcessEngineServices().getFormService().getTaskFormData(taskId);
		  List<FormField> fields = tfd.getFormFields();
		  String txtDemoData = "";
		  String mentor = "";
		  for(FormField f:fields) {
			  if(f.getLabel().equals("Nastavnik diplomskog")) {
				  mentor = (String) f.getValue().getValue();
				  processInstanceCtx.setVariable("mentor", processInstanceCtx.getProcessEngineServices().getIdentityService().createUserQuery().userId(mentor).singleResult());
			  }
			  if(!txtDemoData.equals("")) txtDemoData+=" | ";
			  txtDemoData+=f.getValue().getValue();
		  }
		  //DemoData myBeanData = new DemoData();
		  //System.out.println(txtDemoData);
		  //myBeanData.setTxt(txtDemoData);
		  //DemoData dbBeanData = demoRepository.save(myBeanData);
//		  if(dbBeanData!=null) {
//			  System.out.println("Moj custom bean snimljen u DB");
//			  //ako ga je snimio u bazu, ovaj bean sada ima i ID
//			  //a mogu da ga koristim i kao procesnu varijablu
//			  processInstanceCtx.setVariable("myBean", dbBeanData);
//		  }
		  String currentUser = processInstanceCtx.getVariable("userId").toString();
		  processInstanceCtx.setVariable("student", processInstanceCtx.getProcessEngineServices().getIdentityService().createUserQuery().userId(currentUser).singleResult());
		  processInstanceCtx.setVariable("message", "Task "+delegateTask.getName()+" kompletirao user: "+delegateTask.getAssignee());
		
	  }
}