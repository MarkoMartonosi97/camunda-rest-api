package com.marko.camundarestapis.handlers;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

@Service
public class ClanoviKomisijeCitajuRadStartHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
//		DelegateExecution processInstanceCtx = delegateTask.getExecution();
//		for(User clan2 : (List<User>)processInstanceCtx.getVariable("clanoviKomisije")) {
//			  //System.out.println("Clan" + clan2.getId());
//		  }
		
	}

	
}
