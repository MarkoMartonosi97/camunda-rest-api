package com.marko.camundarestapis.handlers;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.stereotype.Service;

import com.mysql.cj.x.protobuf.MysqlxCrud.Delete;

@Service
public class ClanoviKomisijeOdobravajuTerminHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution processInstanceCtx = delegateTask.getExecution();
		String taskId = delegateTask.getId();
		TaskFormData tfd = delegateTask.getProcessEngineServices().getFormService().getTaskFormData(taskId);
		List<FormField> fields = tfd.getFormFields();
		//System.out.println("USER COMPLETED: " + (String)processInstanceCtx.getVariable("userId") + " With:" + (boolean)fields.get(0).getValue().getValue());
		boolean odgovaraTermin =  (boolean)fields.get(0).getValue().getValue();
		if(!odgovaraTermin) {
			String currentUserId =  (String)processInstanceCtx.getVariable("userId");
			//List<String> clanoviKomisijeOdustanak = (List<String>)processInstanceCtx.getVariable("izbaceniClanoviKomisije");
			//clanoviKomisijeOdustanak.add(currentUserId);
			processInstanceCtx.setVariable("izbaceniClanKomisije", currentUserId);
			processInstanceCtx.setVariable("daliterminodgovara", false);
//			processInstanceCtx.getProcessEngineServices()
//				.getRuntimeService()
//				.createProcessInstanceModification(processInstanceCtx.getId())
//				.startBeforeActivity("UserTask_0jmgsvg")
//				.execute();
			
			//processInstanceCtx.getProcessEngineServices().getTaskService().deleteTask(taskId);
			//processInstanceCtx.getProcessEngineServices().getRuntimeService().deleteProcessInstance(delegateTask.getProcessInstanceId(), null);
		}
	}
}
