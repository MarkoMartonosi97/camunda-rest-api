package com.marko.camundarestapis.handlers;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.stereotype.Service;

@Service
public class StudentRadiDiplomskiHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		
		DelegateExecution processInstanceCtx = delegateTask.getExecution();
		  String taskId = delegateTask.getId();
		  TaskFormData tfd = delegateTask.getProcessEngineServices().getFormService().getTaskFormData(taskId);
		  List<FormField> fields = tfd.getFormFields();
		  //System.out.println(fields.isEmpty());
		  for(FormField f:fields) {
			  if(f.getId().equals("diplomskiRad")) {
				  String diplomski = (String) f.getValue().getValue();
				  processInstanceCtx.setVariable("diplomski", diplomski);
			  }
		  }
	}
}
