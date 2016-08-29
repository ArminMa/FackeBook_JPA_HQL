package org.kth.HI1034.service.impl;


import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.AppKeyFactory;
import org.kth.HI1034.controller.util.MediaTypes;
import org.kth.HI1034.model.domain.user.FaceUserPojo;
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
import org.kth.HI1034.security.util.PasswordSaltUtil;
import org.kth.HI1034.security.util.CipherUtils;
import org.kth.HI1034.security.util.KeyUtil;
import org.kth.HI1034.service.KeyService;
import org.kth.HI1034.service.RegisterService;
import org.kth.HI1034.util.GsonX;
import org.kth.HI1034.util.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	private UserDetachedRepository postUserRepo;

	@Autowired
	private KeyService keyService;

	@Value("${server.secretKey}")
	public String serverSecretKey;

	@Override
	public ResponseEntity<?> registerNewUser(TokenPojo tokenPojo) throws GeneralSecurityException {


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


		FaceUserPojo faceUserPojo = GsonX.gson.fromJson(payload, FaceUserPojo.class);

		Assert.notNull(faceUserPojo, "RegisterServiceImpl.registerNewUser the faceUserPojo could not be parsed from token");

        FaceUser faceUserAvailability = userRepository.findOneUserByEmailOrUsername(faceUserPojo.getEmail(), faceUserPojo.getUsername());

        if( faceUserAvailability!= null){
            if(faceUserAvailability.getEmail().equals(faceUserPojo.getEmail()) && faceUserAvailability.getUsername().equals(faceUserPojo.getUsername()) ){
                return ResponseEntity.status(HttpStatus.IM_USED)
                        .contentType(MediaTypes.JsonUtf8)
                        .body("{email: im_used, username: im: im_used}");
            }
            else if(faceUserAvailability.getEmail().equals(faceUserPojo.getEmail()) ){
                return ResponseEntity.status(HttpStatus.IM_USED)
                        .contentType(MediaTypes.JsonUtf8)
                        .body("{ email: im_used }");
            }
            else if(  faceUserAvailability.getUsername().equals(faceUserPojo.getUsername()) ){
                return ResponseEntity.status(HttpStatus.IM_USED)
                        .contentType(MediaTypes.JsonUtf8)
                        .body("{ username: im: im_used }");
            }

            else return ResponseEntity.status(HttpStatus.IM_USED)
                        .contentType(MediaTypes.JsonUtf8)
                        .body(faceUserPojo); // this should never be returned
        }

		String decryptedShareKey = CipherUtils.decryptWithPrivateKey(faceUserPojo.getUserServerKeyPojo().getSharedKey() , AppKeyFactory.getPrivateKey());

		faceUserPojo.getUserServerKeyPojo().setSharedKey(decryptedShareKey);

		// salt the password with servers secret key or some other salt method
/*		String password = PasswordSaltUtil.encryptSalt( "password", "registerTest@gmail.com"+"password" );*/
		String password = null;

		password = PasswordSaltUtil.generateHmacSHA256Signature( faceUserPojo.getPassword(), serverSecretKey );

		Assert.notNull(password, "RegisterServiceImpl.registerNewUser the password could not be salted");

		faceUserPojo.setPassword(password);



		FaceUser faceUserEntity =  userRepository.save( Converter.convert(faceUserPojo) );
		userRepository.flush();

		Assert.notNull(faceUserEntity, "RegisterServiceImpl.registerNewUser Could not save User 108?");

		postUserRepo.save(new UserDetached(faceUserPojo.getEmail()));
		postUserRepo.flush();



		UserServerKeyPojo userServerKeyPojo = keyService.save(faceUserPojo.getUserServerKeyPojo());

		Assert.notNull(faceUserEntity, "RegisterServiceImpl.registerNewUser Could not save userServerKeyPojo 117?");

		faceUserPojo = Converter.convert(faceUserEntity);

		AuthorityPojo authorityPojo = new AuthorityPojo(Role.ROLE_USER);
		faceUserPojo.setAuthorities(Arrays.asList(authorityPojo));

		List<Authority> authorities = faceUserPojo.getAuthorities().stream().map(Converter::convert).collect(Collectors.toList());
		Assert.notNull(authorities, "could not convert faceUserPojo.getAuthorities() == null?");
		Assert.notNull(authorities, "could not convert faceUserPojo.getAuthorities() is Empty?");

		List<UserAuthority> userAuthorities = new ArrayList<>();
		for(Authority A: authorities){
			userAuthorities.add(new UserAuthority(faceUserEntity, A));
		}
		userAuthorities = userAuthorityRepo.save(userAuthorities);
		userAuthorityRepo.flush();

		Assert.notNull(userAuthorities, "could not save faceUserEntity.getAuthorities() == null?");
		Assert.notEmpty(userAuthorities, "could not save faceUserEntity.getAuthorities() is Empty?");

		SecretKey secretKey = KeyUtil.SymmetricKey.getSecretKeyFromString(userServerKeyPojo.getSharedKey());
		faceUserPojo.setUserServerKeyPojo(userServerKeyPojo);

		tokenPojo = new TokenPojo();
		tokenPojo.setIssuer("fackeBook.org");
		tokenPojo.setAudience(faceUserPojo.getEmail());
		tokenPojo.setSubject("you are registered and Authenticated");

		Map<String , String> sendPayload = new HashMap<>( );
		sendPayload.put("payload", faceUserPojo.toString());

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
			throw new GeneralSecurityException(e.getMessage(), e.getCause());
		}


		tokenPojo.setToken(JWT);


		return ResponseEntity.ok()
                .contentType(MediaTypes.JsonUtf8)
                .body(tokenPojo.toString());

	}

	/**
	 * Check Repository so that userName and Email is unique
	 *
	 * @return true if the registration can start
	 */
	@Override
	public Boolean startRegistration(FaceUserPojo faceUserPojo) {


		List<FaceUser> faceUsers = this.userRepository.findUserByUsernameOrEmail(
				faceUserPojo.getUsername(), faceUserPojo.getEmail());

		if (faceUsers == null || faceUsers.isEmpty()) return true;

		return false;

	}

	@Override
	public ResponseEntity<?> emailExist(FaceUserPojo faceUserPojo) {

		if(faceUserPojo == null || faceUserPojo.getEmail() == null ){
			ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return (userRepository.findByEmail(faceUserPojo != null ? faceUserPojo.getEmail() : null) != null) ?
				ResponseEntity.status(HttpStatus.FOUND).build() :
				ResponseEntity.status(HttpStatus.NOT_FOUND).build();


	}

	@Override
	public Boolean userNameExist(FaceUserPojo faceUserPojo) {
		return null;
	}


}
