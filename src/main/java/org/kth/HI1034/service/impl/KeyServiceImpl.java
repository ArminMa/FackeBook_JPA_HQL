package org.kth.HI1034.service.impl;

import org.kth.HI1034.AppKeyFactory;
import org.kth.HI1034.AppPublicKeys;
import org.kth.HI1034.exceptions.UserNameOrEmailExistExeption;
import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.repository.UserKeyRepository;
import org.kth.HI1034.model.domain.jwt.UserServerKey;
import org.kth.HI1034.model.domain.jwt.UserServerKeyPojo;
import org.kth.HI1034.security.util.ciperUtil.CipherUtils;
import org.kth.HI1034.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class KeyServiceImpl implements KeyService {


	private static final AppPublicKeys appPublicKeys = new AppPublicKeys( AppKeyFactory.getpublicRsaWebKeyAsJson(), AppKeyFactory.getpublicEllipticWebKeyAsJson() );


	@Autowired
	private UserKeyRepository userServerKeyRepository;


	@Override
	public AppPublicKeys getAppPublicKeys() {
		return appPublicKeys;
	}

	@Override
	public UserServerKeyPojo registerUserServerKey(UserServerKeyPojo userServerKeyPojo) {

		if(userServerKeyRepository.save(Converter.convert(userServerKeyPojo)) != null) return userServerKeyPojo;
		else return null;
	}

	@Override
	public UserServerKeyPojo findUserServerKey(UserServerKeyPojo userServerKeyPojo) {
		UserServerKey userServerKey = userServerKeyRepository.findByEmail(userServerKeyPojo.getEmail());
		if(userServerKey != null ){
			return Converter.convert(userServerKey);
		}
		throw new ResourceNotFoundException("could not find the keys to user:-> " + userServerKeyPojo.getEmail());

	}

	@Override
	public boolean userExists(UserServerKeyPojo userServerKeyPojo) {
		return userServerKeyRepository.findByEmail(userServerKeyPojo.getEmail()) != null;
	}

	@Override
	public UserServerKeyPojo save(UserServerKeyPojo userServerKeyPojo) {

		Assert.notNull(userServerKeyPojo, "\n\nthe sharedKey could not be parsed? what\n\n");
		Assert.notNull(userServerKeyPojo.getEmail(), "\n\nthe sharedKey Email could not be parsed? what\n\n");
		Assert.notNull(userServerKeyPojo.getSharedKey(), "\n\nthe encryptedSharedKey could not be parsed? what\n\n");

		String decryptedSharedKey = null;
		try {
			decryptedSharedKey = CipherUtils.decryptWithPrivateKey(userServerKeyPojo.getSharedKey(), AppKeyFactory.getRsaWebKey().getPrivateKey());
		} catch (Exception e) {
			e.printStackTrace();

		}



		Assert.notNull( decryptedSharedKey, "why is this not decrypted?" );

		userServerKeyPojo.setSharedKey(decryptedSharedKey);

		if(userExists(userServerKeyPojo) )
			throw new UserNameOrEmailExistExeption("the email is already registered");


		UserServerKey userServerKey = userServerKeyRepository.save(Converter.convert(userServerKeyPojo));
		if(userServerKey != null) return Converter.convert(userServerKey);

		throw new ResourceNotFoundException("could not save the key for user:-> " + userServerKeyPojo.getEmail());
	}


}
