package com.marko.camundarestapis.handlers;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.stereotype.Service;

@Service
public class ClanoviKomisijeCitajuRadHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution processInstanceCtx = delegateTask.getExecution();
		String taskId = delegateTask.getId();
		TaskFormData tfd = delegateTask.getProcessEngineServices().getFormService().getTaskFormData(taskId);
		List<FormField> fields = tfd.getFormFields();
		//System.out.println("Trenutno stanje dorade" + processInstanceCtx.getVariable("potrebnaDorada3"));
		//System.out.println("Trenutno stanje dorade2" + processInstanceCtx.getVariable("dalijedoradapotrebna"));
		boolean potrebnaDorada =  (boolean)fields.get(0).getValue().getValue();
		if(potrebnaDorada) {
			processInstanceCtx.setVariable("dalijedoradapotrebna", true);
		}
		
		//System.out.println("Izmenjen stanje dorade; " + processInstanceCtx.getVariable("dalijedoradapotrebna"));
	}
}
