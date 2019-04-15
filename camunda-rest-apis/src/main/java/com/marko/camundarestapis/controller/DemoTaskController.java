package com.marko.camundarestapis.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.synth.SynthSpinnerUI;

import org.camunda.bpm.ProcessEngineService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.impl.FormServiceImpl;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.marko.camundarestapis.model.FormFieldsDto;
import com.marko.camundarestapis.model.FormSubmissionDto;
import com.marko.camundarestapis.model.TaskDto;

import sun.security.provider.certpath.OCSPResponse.ResponseStatus;

@Controller
@RequestMapping("/restapi")
public class DemoTaskController {

	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ManagementService managementService;
	
	
	@GetMapping(path = "/killall")
	public @ResponseBody ResponseEntity kill() {
		for(ProcessInstance instance : runtimeService.createProcessInstanceQuery().active().list()) {
			runtimeService.deleteProcessInstance(instance.getId(), "just couse");
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping(path = "/start", produces = "application/json")
    public @ResponseBody Object get() {
		
//		System.out.println("Proccess instance id :" + pi.getProcessInstanceId());
//		System.out.println("Proccess definition id: " + pi.getProcessDefinitionId());
//		System.out.println("Process bussiness key" + pi.getBusinessKey());
//		
//		System.out.println(
//				runtimeService.createProcessInstanceQuery().active().processInstanceId(pi.getId())
//				);
		
		final String authorization = request.getHeader("Authorization");
		//System.out.println(authorization);
		if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
			// Authorization: Basic base64credentials
			String base64Credentials = authorization.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			// credentials = username:password
			final String[] values = credentials.split(":", 2);
			if(identityService.checkPassword(values[0],values[1])) {
				ProcessInstance pi = runtimeService.startProcessInstanceByKey("test6");
				runtimeService.setVariable(pi.getId(), "userId", values[0]);
				runtimeService.setVariable(pi.getId(), "student", identityService.createUserQuery().userId(values[0]).singleResult());
				Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
				task.setAssignee(values[0]);
				
				TaskFormData tfd = formService.getTaskFormData(task.getId());
				return new FormFieldsDto(task.getId(), pi.getId());
			}
		}
		
		
		
		//return new ResponseEntity("Unauthorized", ResponseStatus.UNAUTHORIZED);
		return null;
		//String loggedInUserId = (String) runtimeService.createVariableInstanceQuery().variableName("userId").list().get(0).getValue();
		//System.out.println("Logged in user: " + runtimeService.createVariableInstanceQuery().variableName("userId").list().get(0).getValue());
		//runtimeService.setVariable(pi.getId(), "userId", loggedInUserId);
//		for(ProcessInstance instance : runtimeService.createProcessInstanceQuery().active().list()) {
//			runtimeService.setVariable(instance.getId(), "userId", userId);
//		}
		
		
		
		//task.setAssignee(runtimeService.getVariable(executionId, variableName));
		
	
		//List<FormField> properties = tfd.getFormFields();
		//return formService.getRenderedTaskForm(task.getId());
        
    }
	
	@GetMapping(path = "/tasks")
	public @ResponseBody Object next() {
		
		//runtimeService.
		
		//list all running/unsuspended instances of the process
//	    ProcessInstance processInstance =
//	        runtimeService.createProcessInstanceQuery()
//	            .processDefinitionKey("prijavaDiplomskog")
//	            .active() // we only want the unsuspended process instances
//	            .list().get(0);
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for(ProcessInstance o : runtimeService.createProcessInstanceQuery().active().list()) {
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(o.getId()).list();
			
			for (Task task : tasks) {
				TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
				//System.out.println("TASK DUE DATE:" + task.getDueDate());
				dtos.add(t);
			}
		}
		
	    
	    //System.out.println(runtimeService.createProcessInstanceQuery().processDefinitionKey("prijavaDiplomskog"));
		//Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
		return dtos;
	}
	
	@GetMapping(path = "/tasks/{processInstanceId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> get(@PathVariable String processInstanceId) {
		
		//System.out.println(runtimeService.getVariable(processInstanceId, "userId"));
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task : tasks) {
			TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
			dtos.add(t);
		}
		
        return new ResponseEntity(dtos,  HttpStatus.OK);
    }
	
	@GetMapping(path = "/task/{taskId}", produces = "application/json")
    public @ResponseBody Object getTask(@PathVariable String taskId) {
		
		final String authorization = request.getHeader("Authorization");
		//System.out.println(authorization);
		if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
			// Authorization: Basic base64credentials
			String base64Credentials = authorization.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			// credentials = username:password
			final String[] values = credentials.split(":", 2);
			if(identityService.checkPassword(values[0],values[1])) {
				
				
				Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
				//System.out.println("Assigned to task" + task.getAssignee());
				TaskFormData tfd = formService.getTaskFormData(task.getId());
				
				//managementService.
				
				
				
				
				if(task.getAssignee() != null) {
					//System.out.println("ASSIGNED TO USER? : ------------");
					if(task.getAssignee().equals(values[0])) {
						return formService.getRenderedTaskForm(taskId);
					}
				} else {
					
					List<IdentityLink> links =  taskService.getIdentityLinksForTask(task.getId());
					
					if(links.size() == 0) {
						return formService.getRenderedTaskForm(taskId);
					}
					
					for (IdentityLink identityLink : links) {
						  String type = identityLink.getType(); 
						  String groupId = identityLink.getGroupId();
						  if(groupId == null || groupId.equals("")) {
							  return formService.getRenderedTaskForm(taskId);
						  }
						  //System.out.println("ASSIGNED TO GROUP : ------------");
						  //System.out.println("Group" + groupId);
						  if (IdentityLinkType.CANDIDATE.equals(type) && groupId != null) {
							  List<Group> userGroups = identityService.createGroupQuery().groupMember(values[0]).list();
							  for(Group g : userGroups) {
								  if(g.getId().equals(groupId)) {
									  return formService.getRenderedTaskForm(taskId);
								  }
							  }
						  }
					}
				}
			}
		}
		
		return null;
		
        //return formService.getRenderedTaskForm(taskId);
    }
	
	@PostMapping(path = "/task/{taskId}", produces = "application/json", consumes = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody Object dto, @PathVariable String taskId) {
		HashMap<String, Object> map = (HashMap<String,Object>)dto;
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/tasks/claim/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity claim(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		String user = (String) runtimeService.getVariable(processInstanceId, "username");
		taskService.claim(taskId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/tasks/complete/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> complete(@PathVariable String taskId) {
		Task taskTemp = taskService.createTaskQuery().taskId(taskId).singleResult();
		//ovde bi naravno morao ici kod koji popunjava polja forme ako je task imao
		//u tom slucaju morali bi postojati i request parametri ili request body
		taskService.complete(taskId);
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(taskTemp.getProcessInstanceId()).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task : tasks) {
			TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
			dtos.add(t);
		}
        return new ResponseEntity<List<TaskDto>>(dtos, HttpStatus.OK);
    }
	
//	private HashMap<String, Object> mapListToDto(Object list)
//	{
//		System.out.println(list.getClass());
//		LinkedHashMap<String,String> myMap = (LinkedHashMap<String,String>)list;
//		return myMap;
////		HashMap<String, Object> map = new HashMap<String, Object>();
////		String[] lis = ((String)list).replace("{", "").replace("}", "").split(",");
////		//for(Object o : (String)list.)
////		for(int i=0;i<lis.length;i++) {
////			map.put(lis[i].split("=")[0], lis[i].split("=")[1]);
////		}
//		
////		HashMap<String, Object> map = new HashMap<String, Object>();
////		for(FormSubmissionDto temp : list){
////			map.put(temp.getFieldId(), temp.getFieldValue());
////		}
//		
//		//return map;
//	}
//	private 
//	if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
//	    // Authorization: Basic base64credentials
//	    String base64Credentials = authorization.substring("Basic".length()).trim();
//	    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
//	    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
//	    // credentials = username:password
//	    final String[] values = credentials.split(":", 2);
//	}
}
