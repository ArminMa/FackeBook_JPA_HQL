package org.kth.HI1034.controller;

import org.kth.HI1034.JWT.TokenPojo;
import org.kth.HI1034.controller.util.MediaTypes;
import org.kth.HI1034.model.domain.entity.user.FaceUser;
import org.kth.HI1034.model.domain.repository.FaceUserRepository;
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
import java.util.List;

@RestController
@RequestMapping("/user")
public class RegisterController {

	@Autowired
	private RegisterService registerService;

	@Autowired
	private FaceUserRepository userRepository;




	@RequestMapping("/getAll")
	public List<FaceUser> characters() {
		return userRepository.findAll();
	}

//	//todo only register user email and userName, this while only register the user with a personal KeyPair
//	//todo check i user exist in user list. if Yes request is denied else next
//	//todo check if user exist in userKey repo, if no generate the OK to creat user Token
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

		System.out.println("\n\nRegisterController.57\n\n");

		tokenPojo = registerService.registerNewUser(tokenPojo);

		return ResponseEntity.ok()
				.contentType(MediaTypes.JsonUtf8)
				.body(tokenPojo);
	}
//
//	// validate that the token is givin the user permision to creat a acount
//	@RequestMapping(value = "/register/user",
//			method = RequestMethod.POST,
//			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
//			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@ResponseStatus(HttpStatus.ACCEPTED)
//	public @ResponseBody
//	ResponseEntity<?> registerUser(
//			@RequestBody TokenPojo tokenPojo,
//			HttpServletRequest request,
//			HttpServletResponse response) throws JoseException, InvalidJwtException {
//
//		System.out.println("\n\n" +
//				MoreObjects.toStringHelper(tokenPojo)
//						.toString() + "\n\n");
//
//		registerService.
//
//		return ResponseEntity.ok()
//				.contentType(MediaTypes.JsonUtf8)
//				.header("X-AuthToken", token)
//				.body(faceuserPojo);
//	}

//	@RequestMapping(value = "/login",
//			method = RequestMethod.POST,
//			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
//			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public @ResponseBody ResponseEntity<?> authenticationRequest(@RequestBody FaceuserPojo user) {
//
//		// Perform the authentication
//		Authentication authentication = this.authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(
//						authenticationRequest.getUsername(),
//						authenticationRequest.getPassword()
//				)
//		);
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		// Reload password post-authentication so we can generate token
//		UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//		String token = this.tokenUtils.generateToken(userDetails, device);
//		AuthenticationResponse authenticationToken = new AuthenticationResponse(token);
//
//
//
//
//		// Return the token
////		return ResponseEntity.ok(new AuthenticationResponse(token));
//		return ResponseEntity.ok()
//				.contentType(applicationJsonMediaType)
//				.header("X-AuthToken", authenticationToken.getToken())
//				.body(authenticationToken);
//	}

}
