package org.kth.HI1034.service.impl;

import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.authority.UserAuthorityRepository;
import org.kth.HI1034.model.domain.user.FaceuserPojo;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKeyPojo;
import org.kth.HI1034.model.domain.user.FaceUserRepository;
import org.kth.HI1034.security.TokenIoUtils;
import org.kth.HI1034.security.util.KeyUtil;
import org.kth.HI1034.service.AuthService;
import org.kth.HI1034.service.KeyService;
import org.kth.HI1034.util.GsonX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserAuthorityRepository userAuthorityRepo;

	@Autowired
	private FaceUserRepository faceUserRepository;


	@Autowired
	private KeyService keyService;

	@Value("${server.secretKey}")
	public String serverSecretKey;

	@Value("${token.header}")
	private String tokenHeader;

	@Override
	public boolean authenticatUser(FaceuserPojo faceuserPojo) {
		return false;
	}

	@Override
	public FaceuserPojo getAuthentication(HttpServletRequest httpRequest, String userEmail) {
		String token = httpRequest.getHeader(tokenHeader);

		System.out.println("\n\n----------------- AuthServiceImpl.getAuthentication() ----------------------------" +
						"\ntoken = " + token +
				"\ntokenHeader = " + tokenHeader +
				"\nuserEmail = " + userEmail +
				"\n\n");

		UserServerKeyPojo userServerKeyPojo =  keyService.findUserServerKey(userEmail) ;

		if( userServerKeyPojo   != null ){

			SecretKey secretTokenKey =   KeyUtil.getSecretKeyFromString(userServerKeyPojo.getTokenKey() );

			if ( TokenIoUtils.isValid(token, secretTokenKey) ){
				String payload = TokenIoUtils.getPayloadFromJwt(token, secretTokenKey);
				if(payload != null){
					FaceuserPojo faceuserPojo = GsonX.gson.fromJson(payload, FaceuserPojo.class);
					return faceuserPojo;
				}

			}
		}




		return null;
	}

	@Override
	public FaceuserPojo loadUserFromRepository(FaceuserPojo faceuserPojo) {
		System.out.println("\n\n----------------- AuthServiceImpl.loadUserFromRepository-59 ----------------------------\n\n");
		return Converter.convert(	faceUserRepository.findByEmail(faceuserPojo.getEmail()) );

	}
}
