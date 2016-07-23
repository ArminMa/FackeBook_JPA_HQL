package org.kth.HI1034.service.impl;

import org.jose4j.jwk.RsaJsonWebKey;
import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.jwt.UserServerKey;
import org.kth.HI1034.model.domain.jwt.UserServerKeyPojo;
import org.kth.HI1034.model.domain.jwt.UserServerKeyRepository;
import org.kth.HI1034.security.util.ciperUtil.KeyUtil;
import org.kth.HI1034.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@Service
public class KeyServiceImpl implements KeyService{


	@Autowired
	private UserServerKeyRepository userServerKeyRepository;

	@Override
	public RsaJsonWebKey getServerStaticRsaJsonWebKey() {
		return null;
	}

	@Override
	public KeyPair getServerStaticKeyPair() {

		return KeyUtil.generateKeyPair();
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
}
