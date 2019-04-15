package com.marko.camundarestapis.handlers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

@Service
public class MentorOdobravaProduzenjeRokaHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
//		DelegateExecution processInstanceCtx = delegateTask.getExecution();
//		User mentor = (User) processInstanceCtx.getVariable("mentor");
//		//System.out.println("Assign");
//		delegateTask.setAssignee(mentor.getId());
	}

	
}
