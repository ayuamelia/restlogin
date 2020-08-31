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
import org.springframework.web.servlet.ModelAndView;

import com.ayu.restservice.dao.UserMapper;
import com.ayu.restservice.entity.Login;
import com.ayu.restservice.entity.User;

@RestController

public class RestServiceController {

	@Autowired
	UserMapper userMapper;

	PrintStream print = new PrintStream(System.out);

	@Resource
	UserMapper mapper;
	
	@GetMapping({ "/" })
	public ModelAndView login() {
		ModelAndView index = new ModelAndView("index");
		return index;
	}

	@GetMapping("/listUser")
	public List listUser() {
		return mapper.listUser();
	}

	@RequestMapping(value = "/getUserData", method = { RequestMethod.POST })
	public ModelAndView getData(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password) throws Exception {
		ModelAndView welcome = new ModelAndView("welcome");
		ModelAndView index = new ModelAndView("index");
		User userData = (User) userMapper.findUser(username);
		TripleDesBouncyCastle myEncryptor = new TripleDesBouncyCastle();

		if (userData != null) {
			model.addAttribute("username", username);

			String encrypted = myEncryptor.cryptBC(password);
			String decrypted = myEncryptor.decryptBC(encrypted);

			Login login = new Login();
			login.setUserID(username);
			login.setPassword(encrypted);
			print.println("Login data: " + login);
			print.println("encrypted: " + encrypted);
			print.println("decrypted: " + decrypted);

			String url = "http://10.20.215.10:8201/ad-gateways/verify1";

			// create an instance of RestTemplate
			RestTemplate restTemplate = new RestTemplate();

			// create headers
			HttpHeaders headers = new HttpHeaders();
			// set `content-type` header
			headers.setContentType(MediaType.APPLICATION_JSON);
			// set `ClientID` header
			headers.set("ClientID", "5C343DD198F94EB7E05400144FFBD319");

			Map<String, Object> map = new HashMap<>();
			map.put("UserID", login.getUserID());
			map.put("ApplicationID", login.getApplicationID());
			map.put("Password", login.getPassword());

			try {
				// build the request
				HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

				// use `exchange` method for HTTP call
				ResponseEntity<Login> response = restTemplate.postForEntity(url, entity, Login.class);
				if (response.getStatusCode() == HttpStatus.OK) {
					return welcome;
				}
			} catch (Exception e) {
				model.addAttribute("error", "Wrong Password");
				e.printStackTrace();
				return index;
			}
		}
		model.addAttribute("error", "Wrong Username and Password");
		return index;

	}
}
