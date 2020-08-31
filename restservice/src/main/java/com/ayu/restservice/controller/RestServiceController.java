package com.ayu.restservice.controller;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ayu.restservice.dao.UserMapper;
import com.ayu.restservice.entity.User;
import com.ayu.restservice.model.Login;

@RestController

public class RestServiceController {

	@Autowired
	UserMapper userMapper;

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	PrintStream print = new PrintStream(System.out);

	@Resource
	UserMapper um;

	@GetMapping("/listu")
	public List listUser() {
		return um.listUser();
	}

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@RequestMapping(value = "/getUserData", method = { RequestMethod.POST })
	public Login getData(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password) throws Exception {
		model.addAttribute("username", username);
		User userData = (User) userMapper.findUser(username);
		TripleDesBouncyCastle myEncryptor = new TripleDesBouncyCastle();

		if (userData != null) {
			print.println("username found! username:" + username);

			String encrypted = myEncryptor.cryptBC(password, "L4KU3A14DG4T3W4Y");
			String decrypted = myEncryptor.decryptBC(encrypted, "L4KU3A14DG4T3W4Y");

			Login login = new Login();
			login.setUserID(username);
			login.setPassword(encrypted);
			print.println("Get Login data: " + login);
			print.println("encrypted: " + encrypted);
			print.println("decrypted: " + decrypted);

			String url = "https://5f48837457a10f001600dafb.mockapi.io/ad-gateways/verify1/login";

			// create an instance of RestTemplate
			RestTemplate restTemplate = new RestTemplate();

			// create headers
			HttpHeaders headers = new HttpHeaders();
			// set `content-type` header
			headers.setContentType(MediaType.APPLICATION_JSON);
			// set `accept` header
			headers.set("ClientID", "5C343DD198F94EB7E05400144FFBD319");

			Map<String, Object> map = new HashMap<>();
			map.put("UserID", login.getUserID());
			map.put("ApplicationID", login.getApplicationID());
			map.put("Password", login.getPassword());

			// build the request
			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

			// use `exchange` method for HTTP call
			ResponseEntity<Login> response = restTemplate.postForEntity(url, entity, Login.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				return response.getBody();
			} else {
				return null;
			}
		}
		return null;

	}
}

//	public Login toEAI() {
//		String url = "https://5f48837457a10f001600dafb.mockapi.io/ad-gateways/verify1/login";
//
//		// create an instance of RestTemplate
//		RestTemplate restTemplate = new RestTemplate();
//
//		// create headers
//		HttpHeaders headers = new HttpHeaders();
//		// set `content-type` header
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		// set `accept` header
////	    headers.set("ClientID", "5C343DD198F94EB7E05400144FFBD319");
//
//		Map<String, Object> map = new HashMap<>();
//		Login login = new Login();
//		map.put("UserID", login.getUserID());
//		map.put("ApplicationID", login.getApplicationID());
//		map.put("Password", login.getPassword());
//
//		// build the request
//		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
//
//		// use `exchange` method for HTTP call
//		ResponseEntity<Login> response = restTemplate.postForEntity(url, entity, Login.class);
//		if (response.getStatusCode() == HttpStatus.OK) {
//			return response.getBody();
//		} else {
//			return null;
//		}
//	}
//
//}
