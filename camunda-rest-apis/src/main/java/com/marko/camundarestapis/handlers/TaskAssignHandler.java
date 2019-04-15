package com.marko.camundarestapis.handlers;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskAssignHandler implements TaskListener {
	
	  @Autowired 
	  FormService formService;
	  
	  public void notify(DelegateTask delegateTask) {
		  System.out.println("Obavljen assignement taska:"+delegateTask.getId()+" | "+delegateTask.getAssignee());
	  }
}