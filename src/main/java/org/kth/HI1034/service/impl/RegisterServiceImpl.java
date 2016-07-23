package org.kth.HI1034.service.impl;


import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.kth.HI1034.JWT.TokenPojo;
import org.kth.HI1034.JWT.TokenUtils;
import org.kth.HI1034.model.domain.entity.user.FaceUser;
import org.kth.HI1034.model.domain.repository.FaceUserRepository;
import org.kth.HI1034.model.domain.repository.UserKeyRepository;
import org.kth.HI1034.model.pojo.FaceuserPojo;
import org.kth.HI1034.security.ApiKeyFactory;
import org.kth.HI1034.security.util.ciperUtil.JsonWebKeyUtil;
import org.kth.HI1034.service.KeyService;
import org.kth.HI1034.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private FaceUserRepository userRepository;

	@Autowired
	private UserKeyRepository keyRepository;

	@Autowired
	private KeyService keyService;

	@Override
	public TokenPojo registerNewUser(TokenPojo tokenPojo) throws Exception {

		EllipticCurveJsonWebKey ellipticJsonWebKey = ApiKeyFactory.getEllipticJsonWebKey();

//		String sendersPublicKey = CipherUtils.decryptWithPrivateKey(tokenPojo.getSenderJwk(), ellipticJsonWebKey.getPrivateKey());
//		tokenPojo.setSenderJwk(sendersPublicKey);

		tokenPojo.setReceiverJwk(JsonWebKeyUtil.getPrivateEcllipticWebKeyAsJson(ellipticJsonWebKey));

		System.out.println("\n\n\n------------- RegisterServiceImpl? 39 -----------------" +
				"\n" + tokenPojo.getSubject() +
				"\n\n-------------RegisterServiceImpl?-----------------\n\n\n");

		String token = TokenUtils.getPayloadCurveJWK(tokenPojo);


		System.out.println("\n\n\n------------- RegisterServiceImpl? 46 -----------------" +
				"\n" + tokenPojo.getSubject() +
				"\n\n-------------RegisterServiceImpl?-----------------\n\n\n");

		return tokenPojo;

	}

	/**
	 * Check Repository so that userName and Email is unique
	 *
	 * @return true if the registration can start
	 */
	@Override
	public Boolean startRegistration(FaceuserPojo faceuserPojo) {


		List<FaceUser> faceUsers = this.userRepository.findUserByUsernameOrEmail(
				faceuserPojo.getUsername(), faceuserPojo.getEmail());

		if (faceUsers == null || faceUsers.isEmpty()) return true;

		return false;

	}


	@Override

	public Boolean emailExist(FaceuserPojo faceuserPojo) {
		return null;
	}

	@Override
	public Boolean userNameExist(FaceuserPojo faceuserPojo) {
		return null;
	}


}
