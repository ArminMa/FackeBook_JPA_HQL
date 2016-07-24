package org.kth.HI1034.service.impl;


import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.kth.HI1034.AppKeyFactory;
import org.kth.HI1034.JWT.TokenPojo;
import org.kth.HI1034.JWT.TokenUtils;
import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.entity.user.FaceUser;
import org.kth.HI1034.model.domain.jwt.UserServerKeyPojo;
import org.kth.HI1034.model.domain.repository.FaceUserRepository;
import org.kth.HI1034.model.pojo.FaceuserPojo;
import org.kth.HI1034.security.util.ciperUtil.JsonWebKeyUtil;
import org.kth.HI1034.service.KeyService;
import org.kth.HI1034.service.RegisterService;
import org.kth.HI1034.util.GsonX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Key;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private FaceUserRepository userRepository;


	@Autowired
	private KeyService keyService;

	@Override
	public TokenPojo registerNewUser(TokenPojo tokenPojo) throws Exception {

		EllipticCurveJsonWebKey ellipticJsonWebKey = AppKeyFactory.getEllipticWebKey();

//		String sendersPublicKey = CipherUtils.decryptWithPrivateKey(tokenPojo.getSenderJwk(), ellipticJsonWebKey.getPrivateKey());
//		tokenPojo.setSenderJwk(sendersPublicKey);

		tokenPojo.setReceiverJwk(JsonWebKeyUtil.getPrivateEcllipticWebKeyAsJson(ellipticJsonWebKey));

		if(tokenPojo.getToken() == null){
			System.out.println("\n\n\n------------- RegisterServiceImpl? 43 -----------------" +
					"\n" + "token is null?" +
					"\n\n-------------RegisterServiceImpl?-----------------\n\n\n");
		}

		String payload = TokenUtils.EllipticJWT.getPayloadCurveJWK(
				tokenPojo.getIssuer(),
				tokenPojo.getAudience(),
				tokenPojo.getSenderJwk(),
				tokenPojo.getReceiverJwk(),
				tokenPojo.getToken()
		);


		FaceuserPojo faceuserPojo = GsonX.gson.fromJson(payload, FaceuserPojo.class);

		Assert.notNull(faceuserPojo, "the token could not be parsed");



//		Json key = JsonWebKeyUtil.SymmetricKey.stringToSecretKey(faceuserPojo.getUserServerKeyPojo().getSharedKey());

		String encryptedKey = faceuserPojo.getUserServerKeyPojo().getSharedKey();




		// decrypts and saves the shared key;
		UserServerKeyPojo userServerKeyPojo = keyService.save(faceuserPojo.getUserServerKeyPojo());
		FaceUser faceUser = userRepository.save( Converter.convert(faceuserPojo) );

		Key secretKey = JsonWebKeyUtil.SymmetricKey.stringToSecretKey(userServerKeyPojo.getSharedKey());

		faceuserPojo = Converter.convert(faceUser);
		faceuserPojo.setUserServerKeyPojo(userServerKeyPojo);

		tokenPojo = new TokenPojo();
		tokenPojo.setIssuer("registerTest@gmail.com");
		tokenPojo.setAudience("fackebook.se");
		tokenPojo.setSubject("you are registered and Authenticated");

		String JWT = TokenUtils.SymmetricJWT.generateJWT(
				secretKey,
				tokenPojo.getIssuer(),
				tokenPojo.getAudience(),
				tokenPojo.getSubject(),
				faceuserPojo.toString(), 10);

		tokenPojo.setToken(JWT);

		System.out.println("\n\n\n------------- RegisterServiceImpl? 57 -----------------" +
				"\n" + JWT +
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
