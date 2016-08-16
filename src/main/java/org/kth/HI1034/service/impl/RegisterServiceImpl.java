package org.kth.HI1034.service.impl;


import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.AppKeyFactory;
import org.kth.HI1034.security.JWT.TokenJose4jUtils;
import org.kth.HI1034.security.JWT.TokenPojo;
import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.authority.Authority;
import org.kth.HI1034.model.domain.authority.AuthorityPojo;
import org.kth.HI1034.model.domain.authority.UserAuthority;
import org.kth.HI1034.model.domain.authority.UserAuthorityRepository;
import org.kth.HI1034.model.domain.post.UserDetachedRepository;
import org.kth.HI1034.model.domain.post.UserDetached;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKeyPojo;
import org.kth.HI1034.model.domain.user.FaceUserRepository;
import org.kth.HI1034.model.domain.user.FaceuserPojo;
import org.kth.HI1034.security.util.PasswordSaltUtil;
import org.kth.HI1034.security.util.CipherUtils;
import org.kth.HI1034.security.util.KeyUtil;
import org.kth.HI1034.service.KeyService;
import org.kth.HI1034.service.RegisterService;
import org.kth.HI1034.util.GsonX;
import org.kth.HI1034.util.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private FaceUserRepository userRepository;

	@Autowired
	private UserAuthorityRepository userAuthorityRepo;

	@Autowired
	UserDetachedRepository postUserRepo;

	@Autowired
	private KeyService keyService;

	@Value("${server.secretKey}")
	public String serverSecretKey;

	@Override
	public TokenPojo registerNewUser(TokenPojo tokenPojo) throws GeneralSecurityException, JoseException {


		Assert.notNull(tokenPojo, "RegisterServiceImpl.registerNewUser the TokenPojo is null");
		EllipticCurveJsonWebKey ellipticJsonWebKey = AppKeyFactory.getEllipticWebKey();


		tokenPojo.setReceiverKey(TokenJose4jUtils.JsonWebKeyUtil.getPrivateEcllipticWebKeyAsJson(ellipticJsonWebKey));

		if(tokenPojo.getToken() == null){
			System.out.println("\n\n\n------------- RegisterServiceImpl? 45 -----------------" +
					"\n" + "token is null?" +
					"\n\n-------------RegisterServiceImpl?-----------------\n\n\n");
		}

		String payload = TokenJose4jUtils.EllipticJWT.getPayloadCurveJWK(
				tokenPojo.getIssuer(),
				tokenPojo.getAudience(),
				tokenPojo.getSenderKey(),
				tokenPojo.getReceiverKey(),
				tokenPojo.getToken()
		);


		FaceuserPojo faceuserPojo = GsonX.gson.fromJson(payload, FaceuserPojo.class);

		Assert.notNull(faceuserPojo, "RegisterServiceImpl.registerNewUser the faceuserPojo could not be parsed from token");


		String decryptedShareKey = CipherUtils.decryptWithPrivateKey(faceuserPojo.getUserServerKeyPojo().getSharedKey() , AppKeyFactory.getPrivateKey());

		faceuserPojo.getUserServerKeyPojo().setSharedKey(decryptedShareKey);

		// salt the password with servers secret key or some other salt method
/*		String password = PasswordSaltUtil.encryptSalt( "password", "registerTest@gmail.com"+"password" );*/
		String password = null;

		password = PasswordSaltUtil.generateHmacSHA256Signature( faceuserPojo.getPassword(), serverSecretKey );

		Assert.notNull(password, "RegisterServiceImpl.registerNewUser the password could not be salted");

		faceuserPojo.setPassword(password);

		UserServerKeyPojo userServerKeyPojo = keyService.save(faceuserPojo.getUserServerKeyPojo());

		FaceUser faceUserEntity =  userRepository.save( Converter.convert(faceuserPojo) );
		userRepository.flush();

		postUserRepo.save(new UserDetached(faceuserPojo.getEmail(), faceuserPojo.getUsername()));
		postUserRepo.flush();

		faceuserPojo = Converter.convert(faceUserEntity);

		AuthorityPojo authorityPojo = new AuthorityPojo(Role.ROLE_USER);
		faceuserPojo.setAuthorities(Arrays.asList(authorityPojo));

		List<Authority> authorities = faceuserPojo.getAuthorities().stream().map(Converter::convert).collect(Collectors.toList());
		Assert.notNull(authorities, "could not convert faceuserPojo.getAuthorities() == null?");
		Assert.notNull(authorities, "could not convert faceuserPojo.getAuthorities() is Empty?");

		List<UserAuthority> userAuthorities = new ArrayList<>();
		for(Authority A: authorities){
			userAuthorities.add(new UserAuthority(faceUserEntity, A));
		}
		userAuthorities = userAuthorityRepo.save(userAuthorities);
		userAuthorityRepo.flush();

		Assert.notNull(userAuthorities, "could not save faceUserEntity.getAuthorities() == null?");
		Assert.notEmpty(userAuthorities, "could not save faceUserEntity.getAuthorities() is Empty?");

		SecretKey secretKey = KeyUtil.SymmetricKey.getSecretKeyFromString(userServerKeyPojo.getSharedKey());
		faceuserPojo.setUserServerKeyPojo(userServerKeyPojo);

		tokenPojo = new TokenPojo();
		tokenPojo.setIssuer("fackeBook.org");
		tokenPojo.setAudience(faceuserPojo.getEmail());
		tokenPojo.setSubject("you are registered and Authenticated");

		Map<String , String> sendPayload = new HashMap<>( );
		sendPayload.put("payload", faceuserPojo.toString());

		String JWT = null;
		try {
			JWT = TokenJose4jUtils.SymmetricJWT.generateJWT(
					secretKey,
					tokenPojo.getIssuer(),
					tokenPojo.getAudience(),
					tokenPojo.getSubject(),
					sendPayload, 10);
		} catch (JoseException e) {
			e.printStackTrace();
			throw new JoseException(e.getMessage(), e.getCause());
		}


		tokenPojo.setToken(JWT);


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
