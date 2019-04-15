package com.marko.camundarestapis.handlers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

@Service
public class DekanPotvrdjujeZahtevHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
//		DelegateExecution processInstanceCtx = delegateTask.getExecution();
//		//delegateTask.setAssignee((String) processInstanceCtx.getVariable("dekan"));
//		User dekan = (User) processInstanceCtx.getVariable("dekan");
//		 delegateTask.setAssignee(dekan.getId());
	}

	
}
