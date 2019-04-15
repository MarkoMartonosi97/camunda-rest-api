package com.marko.camundarestapis.model;

import java.util.List;

import org.camunda.bpm.engine.form.FormField;

public class FormFieldsDto {
	
	String taskId;
	//List<FormField> formFields;
	//Object renderedForm;
	String processInstanceId;

	
	public FormFieldsDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FormFieldsDto(String taskId, String processInstanceId) {
		super();
		this.taskId = taskId;
		//this.renderedForm = renderedForm;
		this.processInstanceId = processInstanceId;
	}

	

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return
	 */
//	public Object getRenderedForm() {
//		return renderedForm;
//	}
//
//	public void setRenderedForm(Object renderedForm) {
//		this.renderedForm = renderedForm;
//	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
}
