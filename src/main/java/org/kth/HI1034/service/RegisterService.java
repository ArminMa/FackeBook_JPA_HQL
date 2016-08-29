package org.kth.HI1034.service;

import org.kth.HI1034.model.domain.user.FaceUserPojo;
import org.kth.HI1034.security.JWT.TokenPojo;
import org.springframework.http.ResponseEntity;

public interface RegisterService {

	/**
	 * Check Repository so that userName and Email is unique
	 *
	 * @return true if the registration can start
	 */
	Boolean startRegistration(FaceUserPojo faceUserPojo);

	ResponseEntity<?> registerNewUser(TokenPojo tokenPojo) throws Exception;

	ResponseEntity<?> emailExist(FaceUserPojo faceUserPojo);

	Boolean userNameExist(FaceUserPojo faceUserPojo);

}
