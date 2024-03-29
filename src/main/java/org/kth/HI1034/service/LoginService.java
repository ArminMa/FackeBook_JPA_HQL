package org.kth.HI1034.service;


import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.security.JWT.TokenPojo;
import org.kth.HI1034.model.domain.user.FaceuserPojo;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface LoginService {

	ResponseEntity<?> loginUser(TokenPojo tokenPojo) throws JoseException, MalformedClaimException, InvalidJwtException, GeneralSecurityException, IOException;

	Boolean emailExist(FaceuserPojo faceuserPojo);

}
