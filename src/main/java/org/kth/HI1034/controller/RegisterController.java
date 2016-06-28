//package org.kth.HI1034.Controller;
//
//import com.google.common.base.MoreObjects;
//import org.kth.HI1034.model.mail.User;
//import org.kth.HI1034.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class RegisterController {
//
//	@Autowired
//	private UserRepository repository;
//
//
//
//
//	@RequestMapping("/users")
//	Set<User> characters() {
//		return repository.findAll();
//	}
//
//	@RequestMapping(value = "/users/register",
//			method = RequestMethod.POST,
//			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
//			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@ResponseStatus(HttpStatus.ACCEPTED)
//	void registerUser( User user) {
//		System.out.println("\n\n" +
//		                MoreObjects.toStringHelper(user)
//		                .toString() + "\n\n");
//
//		repository.save(user);
//	}
//
//}
