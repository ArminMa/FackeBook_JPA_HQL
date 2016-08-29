package org.kth.HI1034.controller;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.model.domain.user.FaceUserPojo;
import org.kth.HI1034.model.pojo.Ping;
import org.kth.HI1034.security.JWT.TokenPojo;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.service.FaceUserService;
import org.kth.HI1034.service.LoginService;
import org.kth.HI1034.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	private LoginService loginService;

	@Autowired
	private FaceUserService faceUserService;




    @RequestMapping(
            value = "/getAll",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            method = RequestMethod.GET)
	public ResponseEntity<?> characters() {

		return faceUserService.getAllUsers();


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

		return registerService.registerNewUser(tokenPojo);
	}















	@RequestMapping(
			value = "/userEmailExist/{email:.+}",
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
			consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
			method = RequestMethod.POST)

	public @ResponseBody ResponseEntity<?> userEmailExist(
			@RequestHeader(name = "jwt", defaultValue = "where is the Token?") String jwt,
			@PathVariable("email") String email,
			@RequestBody Ping ping,
			HttpServletRequest request,
			HttpServletResponse response) {

		return faceUserService.emailExist(new FaceUserPojo(email, null));
	}

    @RequestMapping(
            value = "/emailExists/{email:.+}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> emailExist(
            @PathVariable("email") String email,
            HttpServletRequest request,
            HttpServletResponse response) {

        return faceUserService.emailExist(new FaceUserPojo(email, null));
    }


    @RequestMapping(
            value = "/findByEmail/{email:.+}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            method = RequestMethod.GET)

    public @ResponseBody ResponseEntity<?> findByEmail(
            @PathVariable("email") String email,
            HttpServletRequest request,
            HttpServletResponse response) {

        return faceUserService.finUserByEmail(email);
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






    @RequestMapping(
            value = "/findUserSimilarToThis({searchValue}",
            method = RequestMethod.POST,

            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    ResponseEntity<?> findUserSimilarToThis(
            @PathVariable("searchValue") String searchValue,
            HttpServletRequest request,
            HttpServletResponse response)  {

        return faceUserService.findUserSimilarToThis(searchValue);
    }

















    @RequestMapping(
            value = "/finUserByEmail/{email:.+}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            method = RequestMethod.GET)

    public
    @ResponseBody
    ResponseEntity<?> finUserByEmail(
            @PathVariable("email") String email,
            HttpServletRequest request,
            HttpServletResponse response) {

        return faceUserService.finUserByEmail( email );
    }

}
