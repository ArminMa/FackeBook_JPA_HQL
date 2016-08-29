package org.kth.HI1034.service;


import org.kth.HI1034.model.domain.user.FaceUserPojo;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

	boolean authenticatUser(FaceUserPojo faceUserPojo);

	FaceUserPojo getAuthentication(HttpServletRequest httpRequest, String email);

	FaceUserPojo loadUserFromRepository(FaceUserPojo faceUserPojo);
}
