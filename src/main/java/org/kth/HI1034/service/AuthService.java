package org.kth.HI1034.service;


import org.kth.HI1034.model.domain.user.FaceuserPojo;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

	boolean authenticatUser(FaceuserPojo faceuserPojo);

	FaceuserPojo getAuthentication(HttpServletRequest httpRequest, String email);

	FaceuserPojo loadUserFromRepository(FaceuserPojo faceuserPojo);
}
