package com.marko.camundarestapis.handlers;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.context.ProcessApplicationClassloaderInterceptor;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

@Service
public class ReferentPregledaZahtevHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		 DelegateExecution processInstanceCtx = delegateTask.getExecution();
		 //System.out.println(processInstanceCtx.getProcessEngineServices().getRuntimeService().getva);
//		 List<ProcessInstance> instances =  processInstanceCtx.getProcessEngineServices().getRuntimeService().createProcessInstanceQuery().list();
//		 for(ProcessInstance instance : instances ) {
//			 processInstanceCtx.getProcessEngineServices().getRuntimeService().getVariable(instance.get, variableName)
//			 
//		 }
		 String currentUser = processInstanceCtx.getVariable("userId").toString();
		 //System.out.println("TO SET REFERENT");
		 processInstanceCtx.setVariable("referent", processInstanceCtx.getProcessEngineServices().getIdentityService().createUserQuery().userId(currentUser).singleResult());
		 //System.out.println("HAVE SET REFERENT");
	}
}
