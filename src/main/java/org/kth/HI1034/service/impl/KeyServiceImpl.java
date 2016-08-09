package org.kth.HI1034.service.impl;

import org.kth.HI1034.AppKeyFactory;
import org.kth.HI1034.AppPublicKeys;
import org.kth.HI1034.exceptions.UserNameOrEmailExistExeption;
import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.keyUserServer.UserKeyRepository;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKey;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKeyPojo;
import org.kth.HI1034.security.util.KeyUtil;
import org.kth.HI1034.security.util.SignatureKeyAlgorithm;
import org.kth.HI1034.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;

@Service
public class KeyServiceImpl implements KeyService {


	private static final AppPublicKeys appPublicKeys = new AppPublicKeys(
			AppKeyFactory.getpublicRsaWebKeyAsJson(),
			AppKeyFactory.getpublicEllipticWebKeyAsJson(),
			KeyUtil.getKeyAsString( AppKeyFactory.getPublicKey()) );


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
	public UserServerKeyPojo findUserServerKey(String email) {

		UserServerKey userServerKey = userServerKeyRepository.findByEmail(email);

		if(userServerKey != null ){
			return Converter.convert(userServerKey);
		}

		throw new ResourceNotFoundException("could not find the keys to user:-> " + email);

	}

	@Override
	public UserServerKey userExists(UserServerKeyPojo userServerKeyPojo) {
		return userServerKeyRepository.findByEmail(userServerKeyPojo.getEmail());
	}

	@Override
	public UserServerKeyPojo save(UserServerKeyPojo userServerKeyPojo) {

		Assert.notNull(userServerKeyPojo, "\n\nthe sharedKey could not be parsed? what\n\n");
		Assert.notNull(userServerKeyPojo.getEmail(), "\n\nthe sharedKey Email could not be parsed? what\n\n");
		Assert.notNull(userServerKeyPojo.getSharedKey(), "\n\nthe encryptedSharedKey could not be parsed? what\n\n");

		if(userExists(userServerKeyPojo) != null )
			throw new UserNameOrEmailExistExeption("the email is already registered");

		SecretKey secretTokenKey = KeyUtil.SymmetricKey.generateSecretKey( 32, SignatureKeyAlgorithm.Algo.HS256); //needed a (32*8) 256 bit SecretKey
		userServerKeyPojo.setTokenKey(KeyUtil.SymmetricKey.getKeyAsString(secretTokenKey));

		UserServerKey userServerKey = userServerKeyRepository.save(Converter.convert(userServerKeyPojo));
		userServerKeyRepository.flush();
		if(userServerKey != null) return Converter.convert(userServerKey);

		throw new ResourceNotFoundException("could not save the key for user:-> " + userServerKeyPojo.getEmail());
	}

	@Override
	public UserServerKeyPojo update(UserServerKeyPojo userServerKeyPojo) {
		Assert.notNull(userServerKeyPojo, "\n\nthe sharedKey could not be parsed? what\n\n");
		Assert.notNull(userServerKeyPojo.getEmail(), "\n\nthe sharedKey Email could not be parsed? what\n\n");
		Assert.notNull(userServerKeyPojo.getSharedKey(), "\n\nthe encryptedSharedKey could not be parsed? what\n\n");
		Assert.notNull(userServerKeyPojo.getTokenKey(), "\n\nthe encryptedTokenKey could not be parsed? what\n\n");
		UserServerKey userServerKey = userExists(userServerKeyPojo);
		Assert.notNull(userServerKey, "the email is not registered");

		Integer rowsUpdated = updateRepo( userServerKeyPojo );

		UserServerKeyPojo userServerKeyPojoToReturn;

		if(rowsUpdated != null && rowsUpdated == 1){

			UserServerKeyPojo userServerKeyPojo1 = findUserServerKey(userServerKeyPojo.getEmail());

			return userServerKeyPojo1;
		}

		throw new ResourceNotFoundException("could not save the key for user:-> " + userServerKeyPojo.getEmail());

	}

	private Integer updateRepo(UserServerKeyPojo userServerKeyPojo){
		Integer updated = userServerKeyRepository.update(userServerKeyPojo.getEmail(), userServerKeyPojo.getSharedKey(), userServerKeyPojo.getTokenKey());
		userServerKeyRepository.flush();
		return updated;
	}





}
