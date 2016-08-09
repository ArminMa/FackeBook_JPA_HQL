package org.kth.HI1034.controller;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.JWT.TokenPojo;
import org.kth.HI1034.controller.util.MediaTypes;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.model.domain.user.FaceUserRepository;
import org.kth.HI1034.service.LoginService;
import org.kth.HI1034.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private RegisterService registerService;

	@Autowired
	private FaceUserRepository userRepository;

	@Autowired
	private LoginService loginService;



	@RequestMapping("/getAll")
	public List<FaceUser> characters() {
		return userRepository.findAll();
	}


	@RequestMapping(value = "/register",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public @ResponseBody
	ResponseEntity<?> registerUser(
			@RequestBody TokenPojo tokenPojo,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {



		tokenPojo = registerService.registerNewUser(tokenPojo);

		return ResponseEntity.ok()
				.contentType(MediaTypes.JsonUtf8)
				.body(tokenPojo.toString());
	}




	@RequestMapping(value = "/login",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public @ResponseBody
	ResponseEntity<?> loginUser(
			@RequestBody TokenPojo tokenPojo,
			HttpServletRequest request,
			HttpServletResponse response) throws JoseException, MalformedClaimException, InvalidJwtException, GeneralSecurityException, IOException {

		return loginService.loginUser(tokenPojo);
	}






}
