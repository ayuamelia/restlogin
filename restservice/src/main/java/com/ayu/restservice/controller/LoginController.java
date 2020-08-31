package com.ayu.restservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ayu.restservice.dao.UserMapper;
import com.ayu.restservice.entity.User;
//import com.ayu.restservice.dao.UserMapper;
import com.ayu.restservice.model.Login;

import java.io.PrintStream;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
	@Autowired
	UserMapper userMapper;

	private static final Logger logger = LogManager.getLogger(LoginController.class);
	PrintStream print = new PrintStream(System.out);

	@GetMapping({ "/" })
	public String login() {
		return "index";
	}

	

	@PostMapping(value = "http://10.20.215.10:8201/ad-gateways/verify1", consumes = "application/json", produces = "application/json")
	public Login toEAI(@RequestBody Login user, HttpServletResponse response) {
		response.setContentType("application/json");
		response.setHeader("ClientID", "5C343DD198F94EB7E05400144FFBD319");
//		return personService.saveUpdatePerson(person);
		return user;
	}

	@GetMapping({ "/hello" })
	public String hello(Model model,
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		model.addAttribute("name", name);
		return "hello";
	}
	
	@PostMapping("/customHeader")
	ResponseEntity<String> customHeader() {
	    return ResponseEntity.ok()
	        .header("Custom-Header", "foo")
	        .body("Custom header set");
//	    print.println(ResponseEntity);
	}
	
	

	 
}
