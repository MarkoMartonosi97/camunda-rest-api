package com.marko.camundarestapis.types;

import java.io.File;

import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.impl.form.type.SimpleFormFieldType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.camunda.bpm.engine.variable.value.TypedValue;

public class FileFormFieldType extends AbstractFormFieldType  {

	@Override
	public String getName() {
		System.out.println("Get name");
		return null;
	}

	@Override
	public TypedValue convertToFormValue(TypedValue propertyValue) {
		System.out.println("convertToFormValue");
		return null;
	}

	@Override
	public TypedValue convertToModelValue(TypedValue propertyValue) {
		System.out.println("convertToModelValue");
		return null;
	}

	@Override
	public Object convertFormValueToModelValue(Object propertyValue) {
		System.out.println("convertFormValueToModelValue");
		return null;
	}

	@Override
	public String convertModelValueToFormValue(Object modelValue) {
		System.out.println("convertModelValueToFormValue");
		return null;
	}

	
}
