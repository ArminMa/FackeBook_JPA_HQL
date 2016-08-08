package org.kth.HI1034.service.impl;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.AppKeyFactory;
import org.kth.HI1034.security.TokenIoUtils;
import org.kth.HI1034.JWT.TokenJose4jUtils;
import org.kth.HI1034.JWT.TokenPojo;
import org.kth.HI1034.controller.util.MediaTypes;
import org.kth.HI1034.exceptions.ResourceNotFoundException;
import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.authority.Authority;
import org.kth.HI1034.model.domain.authority.AuthorityPojo;
import org.kth.HI1034.model.domain.authority.UserAuthorityRepository;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKeyPojo;
import org.kth.HI1034.model.domain.user.FaceUserRepository;
import org.kth.HI1034.model.domain.user.FaceuserPojo;
import org.kth.HI1034.security.util.PasswordSaltUtil;
import org.kth.HI1034.security.util.CipherUtils;
import org.kth.HI1034.security.util.KeyUtil;
import org.kth.HI1034.security.util.SignatureKeyAlgorithm;
import org.kth.HI1034.service.KeyService;
import org.kth.HI1034.service.LoginService;
import org.kth.HI1034.util.GsonX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private FaceUserRepository userRepository;

	@Autowired
	private UserAuthorityRepository userAuthorityRepo;

	@Autowired
	private KeyService keyService;

	@Value("${server.secretKey}")
	public String serverSecretKey;

	@Value("${token.header}")
	private String tokenHeader;

	@Override
	public ResponseEntity<?> loginUser(TokenPojo tokenPojo) throws JoseException, MalformedClaimException, InvalidJwtException, GeneralSecurityException, IOException {

		Assert.notNull(serverSecretKey, "the @Value(\"${server.secretKey}\") is broken spring problem?");

		// 1 Get a token from user where a secretKey is encrypted with server private key
		// 2 Decrypt the Users SecretKey to use later
		// 3 Get the payload from the token to check if the user has correct email and password
		// 4 Use the created shared secretKey from the user and create a new secretTokenKey and save i in keyRepository
		// 5 Create the Authentication token for the HTTP header with a 256 bit SecretKey created for a the user in question.
		// 6 create a response body with user info an send it with userServerKeyPojo sharedKey
		// 7 send the ResponseEntity with Authtoken in the HTTP header and tokenPojo in the HTTP body

		/** 1 get a token from user where a secretKey is encrypted with server private key */
		Assert.notNull(tokenPojo, "LoginServiceImpl tokenPojo is null?");
		Assert.notNull(tokenPojo.getToken(), "LoginServiceImpl tokenPojo.getToken() is null?");

		/** 2 decrypt the Users SecretKey to use later  */
		String usersEncodedStringSecretKey = CipherUtils.decryptWithPrivateKey( tokenPojo.getSenderKey()  , AppKeyFactory.getKeyPair().getPrivate());
		tokenPojo.setSenderKey(usersEncodedStringSecretKey);
		SecretKey sendersSecretKey = KeyUtil.SymmetricKey.getSecretKeyFromString(usersEncodedStringSecretKey);
		Assert.notNull(usersEncodedStringSecretKey, "LoginServiceImpl 66");
		Assert.notNull(sendersSecretKey, "LoginServiceImpl 68");


		/** 3 Get the payload from the token to check if the user has correct email and password  */
		String payloadKey = "payload";
		String receivedPayload =
				TokenJose4jUtils.SymmetricJWT.getJwtPayload(
						sendersSecretKey,
						tokenPojo.getIssuer(),
						tokenPojo.getAudience(),
						tokenPojo.getSubject(),
						payloadKey,
						tokenPojo.getToken()
				);
		FaceuserPojo faceuserPojo = GsonX.gson.fromJson(receivedPayload, FaceuserPojo.class);
		String password = PasswordSaltUtil.generateHmacSHA256Signature( faceuserPojo.getPassword(), serverSecretKey );


		faceuserPojo.setPassword(password);
		FaceUser F = userRepository.findOneUserByEmailAndPassword(faceuserPojo.getEmail(), faceuserPojo.getPassword());
		FaceuserPojo loggedInUser = Converter.convert( F  );
		Assert.notNull(loggedInUser, "LoginServiceImpl.loginUser: loggedInUser is null?");
		if( !tokenPojo.getIssuer().equals(faceuserPojo.getEmail()) )
			throw new ResourceNotFoundException("could not pars " +
					"\ntokenPojo.getIssuer() = " + tokenPojo.getIssuer() +
					"\nfaceuserPojo.getEmail() = " + faceuserPojo.getEmail());

		/** 4 Use the created shared secretKey from the user and create a new secretTokenKey and save i in keyRepository */
		UserServerKeyPojo userServerKeyPojo = keyService.findUserServerKey(faceuserPojo.getEmail());
		Assert.notNull(userServerKeyPojo, "LoginServiceImpl.userServerKeyPojo:  is the user registered?");
		userServerKeyPojo.setSharedKey(usersEncodedStringSecretKey);
		SecretKey secretTokenKey = KeyUtil.SymmetricKey.generateSecretKey( 32, SignatureKeyAlgorithm.Algo.HS256); //needed a (32*8) 256 bit SecretKey
		userServerKeyPojo.setTokenKey(KeyUtil.SymmetricKey.getKeyAsString(secretTokenKey));

		System.out.println("\n\n----------------- LoginServiceImpl.loginUser().116 ----------------------------" +
				"\nuserServerKeyPojo = " + userServerKeyPojo.toString() + "\n\n");

		userServerKeyPojo = keyService.update(userServerKeyPojo);
		// put the token userServerKeyPojo in loggedInUser but without the secretTokenKey that is used for Authentication

		System.out.println("\n\n----------------- LoginServiceImpl.loginUser().122 ----------------------------" +
				"\nuserServerKeyPojo = " + userServerKeyPojo.toString() + "\n\n");



		userServerKeyPojo.setTokenKey(null);

		loggedInUser.setUserServerKeyPojo(userServerKeyPojo);
		Assert.notNull(userServerKeyPojo, "LoginServiceImpl.loginUser: could not save SecretKey in keyService?");



		/** 5 Create the Authentication token */
		@SuppressWarnings("unchecked")
		List<Authority> authority = (List<Authority>) Converter.convert( userAuthorityRepo.findAuthorityByUserId(loggedInUser.getId()) );
		Assert.notNull( authority , "\n\n LoginServiceImpl.loginUser: could not retrieve authority for user "+ loggedInUser.getEmail());
		List<String> roles = new ArrayList<>();
		Assert.notNull(loggedInUser.getAuthorities(), "LoginServiceImpl.loginUser: loggedInUser.getAuthorities() is null?");
		roles.addAll(loggedInUser.getAuthorities().stream().map(AuthorityPojo::getUserRole).collect(Collectors.toList()));
		// the token is created with the io.jsonwebtoken library
		faceuserPojo.setUserServerKeyPojo(userServerKeyPojo);
		String httpTokenHeader = TokenIoUtils.createAuthJwt(
				"faceBook.org",
				faceuserPojo.getEmail(),
				"Authentication", roles,
				faceuserPojo.toString(),
				secretTokenKey );


		/** 6 create a response body with user info an send it with userServerKeyPojo sharedKey */
		tokenPojo = new TokenPojo();
		tokenPojo.setIssuer("fackebook.se");
		tokenPojo.setAudience("registerTest@gmail.com");
		tokenPojo.setSubject("Authentication");
		Map<String , String> sendPayload = new HashMap<>( );
		sendPayload.put("payload", faceuserPojo.toString());

		String token = TokenJose4jUtils.SymmetricJWT.generateJWT(
				sendersSecretKey,
				tokenPojo.getIssuer(),
				tokenPojo.getAudience(),
				tokenPojo.getSubject(),
				sendPayload,
				20);
		tokenPojo.setToken( token );

		/** 7 send the ResponseEntity with Authtoken in the HTTP header and tokenPojo in the HTTP body */
		return ResponseEntity.ok()
				.contentType(MediaTypes.JsonUtf8)
				.header(tokenHeader, httpTokenHeader)
				.body(tokenPojo.toString());
	}

	@Override
	public Boolean emailExist(FaceuserPojo faceuserPojo) {
		Assert.notNull(faceuserPojo, "user should not be null");
		Assert.notNull(faceuserPojo.getEmail(), "email should not be null");
		return userRepository.findByEmail(faceuserPojo.getEmail()) != null;
	}


}
