package com.marko.camundarestapis.handlers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

@Service
public class CompletedSuccessfullyHandler implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("mailMessage", "Diplomski uspesno polozen. Congratz!");
		
	}

}
