package org.kth.HI1034.service;

import org.kth.HI1034.security.JWT.TokenPojo;
import org.kth.HI1034.model.domain.user.FaceuserPojo;

public interface RegisterService {

	/**
	 * Check Repository so that userName and Email is unique
	 *
	 * @return true if the registration can start
	 */
	Boolean startRegistration(FaceuserPojo faceuserPojo);

	TokenPojo registerNewUser(TokenPojo tokenPojo) throws Exception;

	Boolean emailExist(FaceuserPojo faceuserPojo);

	Boolean userNameExist(FaceuserPojo faceuserPojo);

}
