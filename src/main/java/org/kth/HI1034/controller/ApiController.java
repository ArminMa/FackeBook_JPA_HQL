package org.kth.HI1034.controller;

import io.jsonwebtoken.Claims;
import org.kth.HI1034.controller.util.MediaTypes;
import org.kth.HI1034.model.domain.user.FaceUserPojo;
import org.kth.HI1034.service.AuthService;
import org.kth.HI1034.service.FaceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;



@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private AuthService authService;

	@Autowired
	private FaceUserService faceUserService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "role/{role}", method = RequestMethod.GET)
	public Boolean login(@PathVariable final String role,
			final HttpServletRequest request) throws ServletException {
		final Claims claims = (Claims) request.getAttribute("claims");

		return ((List<String>) claims.get("roles")).contains(role);
	}

	@PreFilter("authenticated")
	@RequestMapping(value = "/authCheck/{email:.+}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)

	public @ResponseBody ResponseEntity<?> authCheck(@RequestHeader(value= "AuthToken" ) String accept,
	                                                 @PathVariable("email") String userEmail,
	                                                 HttpServletRequest request,
	                                                 HttpServletResponse response)   {

			System.out.println("----------- ApiController.authCheck /api/getPosts/{email} invoke -----------");
		FaceUserPojo faceUserPojo;
		if( (faceUserPojo = authService.getAuthentication(request, userEmail)) != null ){
			return ResponseEntity.ok()
					.contentType(MediaTypes.JsonUtf8)
					.body(faceUserPojo.toString());
		}


		return ResponseEntity.badRequest().build();
	}

	@PreFilter("authenticated")
	@RequestMapping(value = "/getPosts/{email:.+}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)

	public @ResponseBody ResponseEntity<?> getPosts(@RequestHeader(value= "AuthToken" ) String authToken,
	                                                @RequestHeader(value= "UserToken" ) String userToken,
	                                                 @PathVariable("email") String userEmail,
	                                                 HttpServletRequest request,
	                                                 HttpServletResponse response)   {

		System.out.println("----------- ApiController.authCheck /api/getPosts/{email} invoke -----------");
		FaceUserPojo faceUserPojo;
		if( (faceUserPojo = authService.getAuthentication(request, userEmail)) != null ){

			return faceUserService.getUserReceivdPosts(userEmail, authToken );

		}


		return ResponseEntity.badRequest().build();
	}

//	@RequestMapping(value = "/getPosts2/{email}",
//			method = RequestMethod.GET,
//			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@ResponseStatus(HttpStatus.ACCEPTED)
//
//	public @ResponseBody ResponseEntity<?> authCheck2(@RequestHeader(value= "AuthToken" ) String accept,
//	                                                 @PathVariable("email") String userEmail,
//	                                                 HttpServletRequest request,
//	                                                 HttpServletResponse response)   {
//
//		if(jwtFilter.faceFilter(accept) != null){
//
//			System.out.println("----------- ApiController.authCheck /api/getPosts/{email} invoke -----------");
//
//			return ResponseEntity.ok()
//					.contentType(MediaTypes.JsonUtf8)
//					.body("{ email: " + userEmail + "}");
//		}
//
//		return ResponseEntity.badRequest().header("AuthToken" , "missing").body("{ AuthToken: AuthToken header is missing}");
//
//	}
}
