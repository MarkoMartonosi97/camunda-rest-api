package com.marko.camundarestapis.handlers;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import com.mysql.cj.x.protobuf.MysqlxCrud.Delete;

@Service
public class RukovodilacOdredjujeNazivTemeClanoveKomisijeHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {

		DelegateExecution processInstanceCtx = delegateTask.getExecution();
		  String taskId = delegateTask.getId();
		  TaskFormData tfd = delegateTask.getProcessEngineServices().getFormService().getTaskFormData(taskId);
		  List<FormField> fields = tfd.getFormFields();
		  String txtDemoData = "";
		  String clan = "";
		  //System.out.println(fields.isEmpty());
		  for(FormField f:fields) {
//			  System.out.println("VAlue:" + f.getValue());
//			  System.out.println("Label" + f.getLabel());
//			  System.out.println("Id" + f.getId());
//			  System.out.println("LABEL: " + f.getLabel());
			  if(f.getId().equals("clanKomisije1") || f.getId().equals("clanKomisije2")) {
				  clan = (String) f.getValue().getValue();
				  User user = processInstanceCtx.getProcessEngineServices().getIdentityService().createUserQuery().userId(clan).singleResult();
				  List<User> clanoviKomisije =  (List<User>) processInstanceCtx.getVariable("clanoviKomisije");
				  clanoviKomisije.add(user);
				  processInstanceCtx.setVariable("clanoviKomisije", clanoviKomisije);
			  }
			  if(!txtDemoData.equals("")) txtDemoData+=" | ";
			  txtDemoData+=f.getValue().getValue();
		  }
		  
//		  for(User clan2 : (List<User>)processInstanceCtx.getVariable("clanoviKomisije")) {
//			  System.out.println("Clan" + clan2.getId());
//		  }
		  //DemoData myBeanData = new DemoData();
		  //System.out.println(txtDemoData);
		  //myBeanData.setTxt(txtDemoData);
		  //DemoData dbBeanData = demoRepository.save(myBeanData);
//		  if(dbBeanData!=null) {
//			  System.out.println("Moj custom bean snimljen u DB");
//			  //ako ga je snimio u bazu, ovaj bean sada ima i ID
//			  //a mogu da ga koristim i kao procesnu varijablu
//			  processInstanceCtx.setVariable("myBean", dbBeanData);
//		  }
		  
		  processInstanceCtx.setVariable("message", "Task "+delegateTask.getName()+" kompletirao user: "+delegateTask.getAssignee());
	}

}
