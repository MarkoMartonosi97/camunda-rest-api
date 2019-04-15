package com.marko.camundarestapis.handlers;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.stereotype.Service;

@Service
public class ClanoviKomisijeDodeljujuOcenuHandler implements TaskListener{
	static int iteration = 0;
	
	
	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution processInstanceCtx = delegateTask.getExecution();
		String taskId = delegateTask.getId();
		TaskFormData tfd = delegateTask.getProcessEngineServices().getFormService().getTaskFormData(taskId);
		List<FormField> fields = tfd.getFormFields();
		String ocena = (String) fields.get(0).getValue().getValue();
		System.out.println("Ocena:" + ocena);
		System.out.println("Current iteration: " + iteration);
		if(ocena.equals("nijePolozio")) {
			processInstanceCtx.setVariable("studentPolozioDiplomski", false);
		} else {
			iteration++;
			int ocenaVal = 0;
			
			switch(ocena) {
				case "sest":
					ocenaVal = 6;
					break;
				case "sedam":
					ocenaVal = 7;
					break;
				case "osam":
					ocenaVal = 8;
					break;
				case "devet":
					ocenaVal = 9;
					break;
				case "deset":
					ocenaVal = 10;
			}
				
			int current = Integer.parseInt(processInstanceCtx.getVariable("ocenaDiplomskog").toString());
			int ocenaNum = current + ocenaVal;
			if(iteration > 1) {
				ocenaNum = ocenaNum / 2;
			}
			System.out.println("Ocena num" + ocenaNum );
			processInstanceCtx.setVariable("ocenaDiplomskog", ocenaNum);
		}
		
	}

	
}
