package com.marko.camundarestapis.handlers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Service
public class MentorOdabravaDoraduHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution processInstanceCtx = delegateTask.getExecution();
		//System.out.println("Potrebna dorada: " + processInstanceCtx.getVariable("potrebnaDorada3"));
		//Vracanje na default vrednost
		processInstanceCtx.setVariable("dalijedoradapotrebna", false);
	}

	
}
