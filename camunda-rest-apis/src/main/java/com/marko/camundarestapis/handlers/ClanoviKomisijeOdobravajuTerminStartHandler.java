package com.marko.camundarestapis.handlers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Service
public class ClanoviKomisijeOdobravajuTerminStartHandler implements TaskListener {
	@Override
	public void notify(DelegateTask delegateTask) {
		
		
		DelegateExecution processInstanceCtx = delegateTask.getExecution();
		String taskId = delegateTask.getId();
		
		//System.out.println("Izbaceni clan: " + processInstanceCtx.getVariable("izbaceniClanKomisije"));
		
		//processInstanceCtx.getProcessEngineServices().getTaskService().deleteTask("Task_0q5ykxy");
		
//		processInstanceCtx.getProcessEngineServices()
//		.getRuntimeService()
//		.createProcessInstanceModification(processInstanceCtx.getId())
//		.cancelAllForActivity(taskId)
//		.execute();
	}
}

