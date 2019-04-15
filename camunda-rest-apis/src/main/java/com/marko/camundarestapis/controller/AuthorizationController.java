package com.marko.camundarestapis.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marko.camundarestapis.model.TaskDto;

@Controller
@RequestMapping("/restapi/authentication")
public class AuthorizationController {

	@Autowired
	IdentityService identityService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@PostMapping(path = "/login")
    public @ResponseBody Object get(@RequestParam("userId") String userId, @RequestParam("password") String password) {
		//identityService.setAuthenticatedUserId("mita");
		//System.out.println(processInstanceId);
		//System.out.println(runtimeService.createProcessInstanceQuery().active().list());
		//System.out.println("Got method");
		
		if(identityService.checkPassword(userId, password)) {
			//runtimeService.setva
			//runtimeService.setVariable(processInstanceId, "userId", userId);
			for(ProcessInstance instance : runtimeService.createProcessInstanceQuery().active().list()) {
				runtimeService.setVariable(instance.getId(), "userId", userId);
			}
			String cred = userId + ":" + password;
			byte[] credDecoded = Base64.getEncoder().encode(cred.getBytes());
			String token = "Basic " + new String(credDecoded, StandardCharsets.UTF_8);
			return token;
		}
		
		
		return new ResponseEntity<String>("Login failed", HttpStatus.UNAUTHORIZED);
    }
	
	
	@PostMapping(path = "/logout")
	public @ResponseBody ResponseEntity logout() {
		for(ProcessInstance instance : runtimeService.createProcessInstanceQuery().active().list()) {
			runtimeService.removeVariable(instance.getId(), "userId");
			return new ResponseEntity<String>("Logout ok", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Logout failed", HttpStatus.METHOD_FAILURE);
	}
	
	
	@PostMapping(path = "/register")
	public @ResponseBody Object register(@RequestParam("userId") String userId, @RequestParam("password") String password) {
		
		//System.out.println(userId);
		User newUser = identityService.newUser(userId);
		newUser.setPassword(password);
		newUser.setFirstName(userId);
		newUser.setLastName(userId);
		newUser.setEmail(userId + "@" + userId + ".com");
		identityService.saveUser(newUser);
		return newUser.getId();
	}
}
